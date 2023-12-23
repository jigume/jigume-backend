package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.entity.Address;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsImage;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MarkerDto {
    private Long goodsId;
    private Long categoryId;
    private Address address;
    private String goodsRepImgUrl;

    public static MarkerDto toMarkerDto(Goods goods) {
        MarkerDto markerDto = new MarkerDto();

        markerDto.setGoodsId(goods.getId());
        markerDto.setCategoryId(goods.getCategory().getId());
        markerDto.setAddress(goods.getAddress());

        String goodsRepImgUrl = goods.getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny().get().getGoodsImgUrl();

        markerDto.setGoodsRepImgUrl(goodsRepImgUrl);

        return markerDto;
    }
}
