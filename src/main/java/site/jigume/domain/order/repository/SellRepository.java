package site.jigume.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Sell;

import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Long> {

    List<Sell> findSellsByMemberIdAndGoodsGoodsStatus(Long memberId, GoodsStatus goodsStatus);

    @Modifying
    @Query("update Sell s set s.isDelete = true where s.goods.id = :goodsId")
    void deleteSell(@Param("goodsId") Long goodsId);
}