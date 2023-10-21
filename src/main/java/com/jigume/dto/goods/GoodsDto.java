package com.jigume.dto.goods;

import com.jigume.entity.goods.Goods;
import com.jigume.entity.goods.GoodsImages;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GoodsDto {

    private Long goodsId;

    private String name;

    private String introduction;

    private String link;

    private Integer goodsPrice;

    private Integer deliveryFee;

    private Long mapX;

    private Long mapY;

    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Long category;

    private Integer realDeliveryFee;

    private boolean isEnd;

    private List<GoodsImagesDto> goodsImagesList = new ArrayList<>();


    public static GoodsDto toGoodsDto(Goods goods) {
        GoodsDto goodsDto = new GoodsDto();

        goodsDto.setGoodsId(goods.getId());
        goodsDto.setName(goods.getName());
        goodsDto.setGoodsPrice(goods.getGoodsPrice());
        goodsDto.setIntroduction(goods.getIntroduction());
        goodsDto.setGoodsLimitCount(goods.getGoodsLimitCount());
        goodsDto.setGoodsLimitTime(goods.getGoodsLimitTime());
        goodsDto.setCategory(goods.getCategory().getId());
        goodsDto.setLink(goods.getLink());
        goodsDto.setDeliveryFee(goods.getDeliveryFee());
        goodsDto.setMapX(goods.getAddress().getMapX());
        goodsDto.setMapY(goods.getAddress().getMapY());
        goodsDto.setEnd(goods.isEnd());
        goodsDto.setGoodsImagesList(GoodsImagesDto.toGoodsImagesDto(goods.getGoodsImagesList()));

        if(goods.getCurrentOrderCount() != 0) goodsDto.setRealDeliveryFee(goods.getDeliveryFee() / goods.getCurrentOrderCount());
        else goodsDto.setRealDeliveryFee(goods.getDeliveryFee());

        return goodsDto;
    }
}
