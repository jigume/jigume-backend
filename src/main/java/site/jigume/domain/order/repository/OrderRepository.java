package site.jigume.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.goods join fetch o.goods.board " +
            "where o.member.id = :memberId and o.goods.goodsStatus = :goodsStatus")
    List<Order> findOrdersByMemberIdAndGoodsGoodsStatus(@Param("memberId") Long memberId,
                                                        @Param("goodsStatus") GoodsStatus goodsStatus);

    Optional<Order> findOrderByMemberIdAndGoodsId(Long memberId, Long goodsId);
}