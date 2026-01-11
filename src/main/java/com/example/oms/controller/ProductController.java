package com.example.oms.controller;

import com.example.oms.dto.CreateProductRequest;
import com.example.oms.dto.ProductResponse;
import com.example.oms.dto.UpdateProductRequest;
import com.example.oms.entity.ProductEntity;
import com.example.oms.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request){
        ProductEntity product = productService.createProduct(request.name(), request.price());
        return toResponse(product);
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id){
        return toResponse(productService.getById(id));
    }

    @GetMapping
    public List<ProductResponse> list(){
        return productService.listProducts().stream().map(this::toResponse).toList();
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request){
        ProductEntity product = productService.updateProduct(id, request.name(), request.price());
        return toResponse(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    private ProductResponse toResponse(ProductEntity product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }
}
