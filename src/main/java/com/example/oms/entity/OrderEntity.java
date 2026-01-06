package com.example.oms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToMany
    private List<ProductEntity> products;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private Instant createdAt;

    protected OrderEntity() {
    }

    public OrderEntity(UserEntity user, List<ProductEntity> products) {
        this.user = user;
        this.products = products;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
