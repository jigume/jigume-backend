package com.jigume.dto.goods;

import com.jigume.entity.goods.GoodsImages;
import lombok.Data;

@Data
public class GoodsImagesDto {

    private byte[] images;

    public static GoodsImagesDto toDto(GoodsImages goodsImage) {
        GoodsImagesDto goodsImagesDto = new GoodsImagesDto();
        goodsImagesDto.images = goodsImage.getImage();

        return goodsImagesDto;
    }
}
