package site.jigume.domain.goods.repository;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.dto.coordinate.MarkerDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.entity.GoodsStatus;

import java.util.List;
import java.util.Optional;

public interface GoodsCoordinateRepository extends JpaRepository<GoodsCoordinate, Long> {

    @Query("select co.goods.id as goodsId, " +
            "co.goods.category.id as categoryId, " +
            "ST_Y(co.coordinate) as latitude, " +
            "ST_X(co.coordinate) as longitude " +
            "from GoodsCoordinate co " +
            "where ST_CONTAINS(:area, co.coordinate) " +
            "and co.goods.goodsStatus = :status " +
            "and co.goods.isDelete = false")
    List<MarkerDto> findMarkerListFromCoordinate(@Param("area") final Polygon area,
                                                 @Param("status") GoodsStatus goodsStatus);

    @Query("select co.goods from GoodsCoordinate co " +
            "join fetch co.goods.sell " +
            "where ST_CONTAINS(:area, co.coordinate) " +
            "and co.goods.goodsStatus = :status " +
            "and co.goods.isDelete = false")
    Slice<Goods> findGoodsByCoordinate(@Param("area") final Polygon area,
                                       @Param("status") GoodsStatus goodsStatus,
                                       Pageable pageable);

    @Query("select co.goods from GoodsCoordinate co " +
            "join fetch co.goods.sell " +
            "where ST_CONTAINS(:area, co.coordinate) " +
            "and co.goods.goodsStatus = :status " +
            "and co.goods.category.id = :categoryId " +
            "and co.goods.isDelete = false")
    Slice<Goods> findGoodsByCoordinateWithCategory(@Param("area") final Polygon area,
                                                   @Param("status") GoodsStatus goodsStatus,
                                                   @Param("categoryId") Long categoryId, Pageable pageable);

    Optional<GoodsCoordinate> findGoodsCoordinateByGoodsId(Long goodsId);
}