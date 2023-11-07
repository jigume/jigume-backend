package com.jigume.domain.order.repository;

import com.jigume.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByMemberId(Long memberId);

}