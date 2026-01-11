package com.example.oms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OrderEntity order;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProductEntity product;

    @Positive
    @Column(nullable = false)
    private int quantity;


    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal priceAtOrderTime;

    protected OrderItemEntity() {
    }

    public OrderItemEntity(OrderEntity order, ProductEntity product, int quantity, BigDecimal priceAtOrderTime) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrderTime = priceAtOrderTime;
    }

    public void increaseQuantity(int delta) {
        this.quantity += delta;
    }

    public void decreaseQuantity(int delta) {
        this.quantity -= delta;
    }

    public BigDecimal lineTotal() {
        return priceAtOrderTime.multiply(BigDecimal.valueOf(quantity));
    }
}
