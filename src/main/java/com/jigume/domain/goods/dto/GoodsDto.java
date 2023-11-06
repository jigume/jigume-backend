package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.entity.GoodsStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GoodsDto {
    private Long goodsId;
    private String goodsName;
    private String hostNickName;
    private Integer hostSellCount;
    private Integer goodsPrice;
    private Integer goodsDeliveryPrice;
    private Integer goodsOrderCount;
    private Integer discountDeliveryPrice;
    private String repImgUrl;
    private Integer goodsStatus;


    @Builder
    public GoodsDto(Long goodsId, String goodsName, String hostNickName, Integer hostSellCount,
                    Integer goodsPrice, Integer goodsDeliveryPrice,
                    Integer goodsOrderCount, String repImgUrl, GoodsStatus goodsStatus) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.hostNickName = hostNickName;
        this.hostSellCount = hostSellCount;
        this.goodsPrice = goodsPrice;
        this.goodsDeliveryPrice = goodsDeliveryPrice;
        this.goodsOrderCount = goodsOrderCount;
        this.discountDeliveryPrice = goodsDeliveryPrice / goodsOrderCount;
        this.repImgUrl = repImgUrl;
        this.goodsStatus = goodsStatus.getStatus();
    }
}
