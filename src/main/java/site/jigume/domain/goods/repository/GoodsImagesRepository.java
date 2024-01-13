package site.jigume.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.GoodsImage;

import java.util.List;
import java.util.Optional;

public interface GoodsImagesRepository extends JpaRepository<GoodsImage, Long> {

    List<GoodsImage> findGoodsImagesByGoodsId(Long goodsId);

    Optional<GoodsImage> findGoodsImageByGoodsIdAndRepimgYn(Long goodsId, Boolean repImgYn);

    @Query("select gi from GoodsImage gi where gi.goodsImgUrl = :defaultImgUrl")
    GoodsImage findDefalutGoodsImage(@Param("defaultImgUrl") String defaultImgUrl);
}