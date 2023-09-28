package com.jigume.dto.goods;

import com.jigume.entity.goods.Goods;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GoodsDto {

    private Long goodsId;

    private String name;

    private String introduction;

    private String link;

    private Integer goodsPrice;

    private Integer deliveryFee;

    private Integer mapX;

    private Integer mapY;

    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Long category;

    private Integer realDeliveryFee;

    private boolean isEnd;


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
        if(goods.getCurrentOrderCount() != 0) goodsDto.setRealDeliveryFee(goods.getDeliveryFee() / goods.getCurrentOrderCount());
        else goodsDto.setRealDeliveryFee(goods.getDeliveryFee());

        return goodsDto;
    }
}
