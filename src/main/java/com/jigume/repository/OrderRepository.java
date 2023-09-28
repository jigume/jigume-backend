package com.jigume.repository;

import com.jigume.entity.order.Order;
import com.jigume.entity.order.OrderStatus;
import com.jigume.entity.order.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByGoodsId(Long goodsId);

    List<Order> findOrdersByMember_IdAndOrderTypeAndOrderStatus(Long memberId,
                                                                OrderType orderType, OrderStatus orderStatus);

    List<Order> findOrdersByMember_IdAndOrderType(Long memberId, OrderType orderType);
}