package com.example.oms.service;

import com.example.oms.entity.ProductEntity;
import com.example.oms.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductEntity addProduct(String name, Long price){
        ProductEntity product = new ProductEntity(name, price);
        return productRepository.save(product);
    }
}
