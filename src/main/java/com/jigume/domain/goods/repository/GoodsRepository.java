package com.jigume.domain.goods.repository;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g where g.id =:goodsId")
    Optional<Goods> findGoodsById(@Param("goodsId") Long goodsId);

    @Query("select g from  Goods g join fetch g.sell left join fetch g.goodsImageList where g.address.mapX > :minX and g.address.mapX < :maxX " +
            "and g.address.mapY > :minY and g.address.mapY < :maxY and g.category.id = :categoryId and g.goodsStatus = :status " +
            "order by g.createdDate desc")
    Page<Goods> findGoodsByCategoryAndMapRangeOrderByCreatedDate(@Param("categoryId") Long categoryId,
                                                                 @Param("minX") double minX, @Param("maxX") double maxX,
                                                                 @Param("minY") double minY, @Param("maxY") double maxY,
                                                                 @Param("status") GoodsStatus goodsStatus,
                                                                 Pageable pageable);

    @Query("SELECT g FROM Goods g WHERE g.address.mapX > :minX AND g.address.mapX < :maxX " +
            "AND g.address.mapY > :minY AND g.address.mapY < :maxY AND g.goodsStatus = :status " +
            "order by g.createdDate desc")
    List<Goods> findGoodsByMapRange(@Param("minX") double minX, @Param("maxX") double maxX,
                                    @Param("minY") double minY, @Param("maxY") double maxY,
                                    @Param("status") GoodsStatus goodsStatus);

    @Query("SELECT g FROM Goods g join fetch g.sell left join fetch g.goodsImageList WHERE g.address.mapX > :minX AND g.address.mapX < :maxX " +
            "AND g.address.mapY > :minY AND g.address.mapY < :maxY AND g.goodsStatus = :status " +
            "order by g.createdDate desc")
    Page<Goods> findGoodsByMapRange(@Param("minX") double minX, @Param("maxX") double maxX,
                                    @Param("minY") double minY, @Param("maxY") double maxY,
                                    @Param("status") GoodsStatus goodsStatus,
                                    Pageable pageable);

    @Query("select g from Goods g join fetch g.sell left join fetch g.goodsImageList where g.id in :goodsIds")
    Page<Goods> findGoodsByIdIn(@Param("goodsIds") List<Long> goodsIds, Pageable pageable);
}