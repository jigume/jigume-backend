package com.jigume.dto.goods;

import com.jigume.entity.goods.GoodsImages;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GoodsImagesDto {

    private String goodsImgUrl;

    private boolean repimgYn;

    public GoodsImagesDto(String goodsImgUrl, boolean repimgYn) {
        this.goodsImgUrl = goodsImgUrl;
        this.repimgYn = repimgYn;
    }

    public static List<GoodsImagesDto> toGoodsImagesDto(List<GoodsImages> goodsImagesList) {
        return goodsImagesList.stream()
                .map(goodsImages ->
                        new GoodsImagesDto(goodsImages.getGoodsImgUrl(), goodsImages.isRepimgYn()))
                .collect(Collectors.toList());
    }
}
