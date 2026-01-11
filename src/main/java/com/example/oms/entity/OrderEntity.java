package com.example.oms.entity;

import com.example.oms.exception.EmptyOrderException;
import com.example.oms.exception.InvalidOrderStatusTransitionException;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItemEntity> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected OrderEntity() {
    }

    public OrderEntity(UserEntity user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }



    public void addProduct(ProductEntity product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }

        Optional<OrderItemEntity> existing =
                items.stream().filter(i -> i.getProduct().getId().equals(product.getId())).findFirst();

        if (existing.isPresent()) {
            existing.get().increaseQuantity(quantity);
            return;
        }

        items.add(new OrderItemEntity(this, product, quantity, product.getPrice()));
    }


    public void removeProduct(Long productId, int quantityToRemove) {
        if (quantityToRemove <= 0) {
            throw new IllegalArgumentException("quantityToRemove must be positive");
        }

        OrderItemEntity item = items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in order: " + productId));

        if (item.getQuantity() <= quantityToRemove) {
            items.remove(item);
        } else {
            item.decreaseQuantity(quantityToRemove);
        }
    }

    public BigDecimal totalPrice() {
        return items.stream()
                .map(OrderItemEntity::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void ensureNotEmpty() {
        if (items.isEmpty()) {
            throw new EmptyOrderException("Order must contain at least one item");
        }
    }

    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus must not be null");
        }
        if (newStatus == status) return;

        // allow only next step in chain
        OrderStatus expectedNext = switch (status) {
            case NEW -> OrderStatus.PAID;
            case PAID -> OrderStatus.SHIPPED;
            case SHIPPED -> OrderStatus.COMPLETED;
            case COMPLETED -> null;
        };

        if (expectedNext == null || newStatus != expectedNext) {
            throw new InvalidOrderStatusTransitionException(
                    "Invalid status transition: " + status + " -> " + newStatus
            );
        }

        if (newStatus == OrderStatus.PAID) {
            ensureNotEmpty();
        }

        this.status = newStatus;
    }

    public List<OrderItemEntity> itemsSortedByLineTotalDesc() {
        return items.stream()
                .sorted(Comparator.comparing(OrderItemEntity::lineTotal).reversed())
                .toList();
    }
}
