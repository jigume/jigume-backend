package com.jigume.repository;

import com.jigume.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByGoodsId(Long goodsId);

    @Query("select o from Order o join fetch o.goods where o.member.id =: memberId")
    List<Order> findOrdersByMemberId(@Param("memberId") Long memberId);
}