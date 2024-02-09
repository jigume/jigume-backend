package site.jigume.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.goods.entity.GoodsImage;

public interface GoodsImagesRepository extends JpaRepository<GoodsImage, Long> {

    @Query("select gi from GoodsImage gi where gi.file.url = :defaultImgUrl")
    GoodsImage findDefalutGoodsImage(@Param("defaultImgUrl") String defaultImgUrl);

    @Modifying
    @Query("update GoodsImage gi set gi.isDelete = true where gi.goods.id = :goodsId")
    void deleteGoodsImage(@Param("goodsId") Long goodsId);
}