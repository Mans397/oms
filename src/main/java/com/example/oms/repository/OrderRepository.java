package com.example.oms.repository;

import com.example.oms.entity.OrderEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserId(Long userId);

    List<OrderEntity> findAllByUserId(Long userId, Sort sort);
}
