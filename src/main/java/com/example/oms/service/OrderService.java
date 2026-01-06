package com.example.oms.service;

import com.example.oms.entity.OrderEntity;
import com.example.oms.entity.ProductEntity;
import com.example.oms.entity.UserEntity;
import com.example.oms.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(UserEntity user, List<ProductEntity> products){
        OrderEntity order = new OrderEntity(user, products);
        return orderRepository.save(order);
    }


}
