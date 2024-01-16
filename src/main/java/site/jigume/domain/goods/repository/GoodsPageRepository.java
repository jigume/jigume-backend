package site.jigume.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;

import java.util.List;

public interface GoodsPageRepository extends JpaRepository<Goods, Long> {

    @Query("select g from  Goods g where g.address.mapX >= :minX and g.address.mapX <= :maxX " +
            "and g.address.mapY >= :minY and g.address.mapY <= :maxY and g.category.id = :categoryId and g.goodsStatus = :status " +
            "order by g.createdDate desc")
    Slice<Goods> findGoodsByCategoryAndMapRangeOrderByCreatedDate(@Param("categoryId") Long categoryId,
                                                                  @Param("minX") double minX, @Param("maxX") double maxX,
                                                                  @Param("minY") double minY, @Param("maxY") double maxY,
                                                                  @Param("status") GoodsStatus goodsStatus,
                                                                  Pageable pageable);

    @Query("select g from Goods g where g.id in :goodsIds")
    Slice<Goods> findGoodsByIdIn(@Param("goodsIds") List<Long> goodsIds, Pageable pageable);
}