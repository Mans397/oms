package com.example.oms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;


    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    protected ProductEntity() {
    }

    public ProductEntity(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void changePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }
}
