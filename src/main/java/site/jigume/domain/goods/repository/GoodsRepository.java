package site.jigume.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.Goods;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select distinct g from Goods g " +
            "left join fetch g.orderList " +
            "join fetch g.sell " +
            "where g.id = :goodsId")
    Optional<Goods> findGoodsByIdWithOrderList(@Param("goodsId") Long goodsId);

    @Query("select distinct g from Goods g " +
            "left join fetch g.orderList " +
            "join fetch g.sell " +
            "join fetch g.board " +
            "where g.id = :goodsId")
    Optional<Goods> findGoodsByIdWithOrderListAndBoard(@Param("goodsId") Long goodsId);

    @Query("select g from Goods g " +
            "where g.id in :goodsIds")
    Slice<Goods> findGoodsByIdIn(@Param("goodsIds") List<Long> goodsIds, Pageable pageable);

    @EntityGraph(attributePaths = "sell")
    Optional<Goods> findGoodsById(Long goodsId);

    @Query("select distinct g from Goods g " +
            "left join fetch g.orderList " +
            "join fetch g.sell " +
            "join fetch g.board " +
            "where g.id = :goodsId")
    Optional<Goods> findGoodsByIdForDelete(@Param("goodsId") Long goodsId);
}