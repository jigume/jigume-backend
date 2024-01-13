package site.jigume.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByMemberId(Long memberId);

    List<Order> findOrdersByMemberIdAndGoodsGoodsStatus(Long memberId, GoodsStatus goodsStatus);

}