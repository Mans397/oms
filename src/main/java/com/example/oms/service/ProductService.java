package com.example.oms.service;

import com.example.oms.entity.ProductEntity;
import com.example.oms.exception.ProductNotFoundException;
import com.example.oms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(String name, BigDecimal price){
        ProductEntity product = new ProductEntity(name, price);
        return productRepository.save(product);
    }

    public ProductEntity getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<ProductEntity> listProducts() {
        return productRepository.findAll();
    }

    public ProductEntity updateProduct(Long id, String name, BigDecimal price) {
        ProductEntity product = getById(id);
        if (name != null && !name.isBlank()) product.rename(name);
        if (price != null) product.changePrice(price);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        ProductEntity product = getById(id);
        productRepository.delete(product);
    }
}
