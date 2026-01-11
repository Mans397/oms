package com.example.oms.exception;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order not found: " + id);
    }
}
