package site.jigume.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g where g.id =:goodsId")
    Optional<Goods> findGoodsById(@Param("goodsId") Long goodsId);

    @Query("select distinct g from Goods g left join fetch g.orderList where g.id = :goodsId")
    Optional<Goods> findGoodsByIdWithOrderList(@Param("goodsId") Long goodsId);

    @Query("select g from Goods g where g.id in :goodsIds")
    Slice<Goods> findGoodsByIdIn(@Param("goodsIds") List<Long> goodsIds, Pageable pageable);
}