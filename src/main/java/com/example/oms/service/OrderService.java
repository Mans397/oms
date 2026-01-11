package com.example.oms.service;

import com.example.oms.entity.OrderEntity;
import com.example.oms.entity.OrderStatus;
import com.example.oms.entity.ProductEntity;
import com.example.oms.entity.UserEntity;
import com.example.oms.exception.OrderNotFoundException;
import com.example.oms.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService){
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public OrderEntity createOrder(Long userId){
        UserEntity user = userService.getById(userId);
        OrderEntity order = new OrderEntity(user);
        return orderRepository.save(order);
    }

    public OrderEntity getById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public List<OrderEntity> listOrdersByUser(Long userId, String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return orderRepository.findAllByUserId(userId);
        }
        Sort sort = switch (sortBy) {
            case "date" -> Sort.by(Sort.Direction.DESC, "createdAt");

            case "total" -> null;
            default -> throw new IllegalArgumentException("Unknown sortBy: " + sortBy + " (use 'date' or 'total')");
        };

        List<OrderEntity> orders = (sort == null)
                ? orderRepository.findAllByUserId(userId)
                : orderRepository.findAllByUserId(userId, sort);

        if ("total".equals(sortBy)) {
            return orders.stream()
                    .sorted((a, b) -> b.totalPrice().compareTo(a.totalPrice()))
                    .toList();
        }
        return orders;
    }

    @Transactional
    public OrderEntity addProduct(Long orderId, Long productId, int quantity) {
        OrderEntity order = getById(orderId);
        ProductEntity product = productService.getById(productId);

        order.addProduct(product, quantity);
        return orderRepository.save(order);
    }

    @Transactional
    public OrderEntity removeProduct(Long orderId, Long productId, int quantityToRemove) {
        OrderEntity order = getById(orderId);
        order.removeProduct(productId, quantityToRemove);
        return orderRepository.save(order);
    }

    @Transactional
    public OrderEntity changeStatus(Long orderId, OrderStatus newStatus) {
        OrderEntity order = getById(orderId);
        order.changeStatus(newStatus);
        return orderRepository.save(order);
    }
}
