package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.entity.GoodsStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GoodsPageDto {

    private Long goodsId;

    private String goodsName;

    private String introduction;

    private String link;

    private Integer goodsPrice;

    private Integer deliveryFee;

    private Double mapX;

    private Double mapY;

    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Long category;

    private Integer realDeliveryFee;

    private GoodsStatus goodsStatus;

    private String hostNickname;

    private Integer hostSellCount;

    private Integer goodsOrderCount;

    private Integer discountDeliveryPrice;

    private Long boardId;

    private List<GoodsImagesDto> goodsImagesList = new ArrayList<>();

    @Builder
    public GoodsPageDto(Long goodsId, String goodsName, String introduction,
                        String link, Integer goodsPrice, Integer deliveryFee,
                        Double mapX, Double mapY, Integer goodsLimitCount,
                        LocalDateTime goodsLimitTime, Long category,
                        Integer realDeliveryFee, GoodsStatus goodsStatus,
                        String hostNickname, Integer hostSellCount,
                        Integer goodsOrderCount, Integer discountDeliveryPrice,
                        Long boardId,
                        List<GoodsImagesDto> goodsImagesList) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.introduction = introduction;
        this.link = link;
        this.goodsPrice = goodsPrice;
        this.deliveryFee = deliveryFee;
        this.mapX = mapX;
        this.mapY = mapY;
        this.goodsLimitCount = goodsLimitCount;
        this.goodsLimitTime = goodsLimitTime;
        this.category = category;
        this.realDeliveryFee = realDeliveryFee;
        this.goodsStatus = goodsStatus;
        this.hostNickname = hostNickname;
        this.hostSellCount = hostSellCount;
        this.goodsOrderCount = goodsOrderCount;
        this.discountDeliveryPrice = discountDeliveryPrice;
        this.goodsImagesList = goodsImagesList;
        this.boardId = boardId;
    }
}
