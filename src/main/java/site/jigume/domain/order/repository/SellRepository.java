package site.jigume.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Sell;

import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Long> {

    List<Sell> findSellsByMemberIdAndGoodsGoodsStatus(Long memberId, GoodsStatus goodsStatus);
}