package site.jigume.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;

import java.util.List;

public interface GoodsMapRepository extends JpaRepository<Goods, Long> {

    @Query("SELECT g FROM Goods g WHERE g.address.mapX >= :minX AND g.address.mapX <= :maxX " +
            "AND g.address.mapY >= :minY AND g.address.mapY <= :maxY AND g.goodsStatus = :status " +
            "order by g.createdDate desc")
    List<Goods> findGoodsByMapRange(@Param("minX") double minX, @Param("maxX") double maxX,
                                    @Param("minY") double minY, @Param("maxY") double maxY,
                                    @Param("status") GoodsStatus goodsStatus);

    @Query("SELECT g FROM Goods g WHERE g.address.mapX >= :minX AND g.address.mapX <= :maxX " +
            "AND g.address.mapY >= :minY AND g.address.mapY <= :maxY AND g.goodsStatus = :status " +
            "order by g.createdDate desc")
    Slice<Goods> findGoodsByMapRange(@Param("minX") double minX, @Param("maxX") double maxX,
                                     @Param("minY") double minY, @Param("maxY") double maxY,
                                     @Param("status") GoodsStatus goodsStatus,
                                     Pageable pageable);

}