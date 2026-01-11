package com.example.oms.controller;

import com.example.oms.dto.*;
import com.example.oms.entity.OrderEntity;
import com.example.oms.entity.OrderItemEntity;
import com.example.oms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request){
        return toResponse(orderService.createOrder(request.userId()));
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id){
        return toResponse(orderService.getById(id));
    }

    // GET /orders?userId=1&sortBy=date
    @GetMapping
    public List<OrderResponse> listByUser(@RequestParam Long userId,
                                         @RequestParam(required = false) String sortBy) {
        return orderService.listOrdersByUser(userId, sortBy).stream().map(this::toResponse).toList();
    }

    @PostMapping("/{id}/items")
    public OrderResponse addItem(@PathVariable Long id, @Valid @RequestBody AddOrderItemRequest request){
        return toResponse(orderService.addProduct(id, request.productId(), request.quantity()));
    }

    @DeleteMapping("/{id}/items")
    public OrderResponse removeItem(@PathVariable Long id, @Valid @RequestBody RemoveOrderItemRequest request){
        return toResponse(orderService.removeProduct(id, request.productId(), request.quantity()));
    }

    @PostMapping("/{id}/status")
    public OrderResponse changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeOrderStatusRequest request){
        return toResponse(orderService.changeStatus(id, request.status()));
    }

    private OrderResponse toResponse(OrderEntity order) {
        List<OrderItemResponse> items = order.getItems().stream().map(this::toItemResponse).toList();
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.totalPrice(),
                items
        );
    }

    private OrderItemResponse toItemResponse(OrderItemEntity item) {
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPriceAtOrderTime(),
                item.lineTotal()
        );
    }
}
