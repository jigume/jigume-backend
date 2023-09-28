package com.jigume.repository;

import com.jigume.entity.goods.GoodsImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsImagesRepository extends JpaRepository<GoodsImages, Long> {

    List<GoodsImages> findGoodsImagesByGoodsId(Long goodsId);
}