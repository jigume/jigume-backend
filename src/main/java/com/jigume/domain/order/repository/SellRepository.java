package com.jigume.domain.order.repository;

import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SellRepository extends JpaRepository<Sell, Long> {

    @Query("select s from Sell s join fetch s.member join fetch s.goods where s.member.id = :memberId")
    List<Sell> findSellsByMemberId(@Param("memberId") Long memberId);

    Integer countSellByMemberId(@Param("memberId") Long memberId);

    List<Sell> findSellsByMemberIdAndGoodsGoodsStatus(Long memberId, GoodsStatus goodsStatus);

    Optional<Sell> findSellByGoodsId(Long goodsId);
}