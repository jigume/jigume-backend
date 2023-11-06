package com.jigume.domain.order.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EndBuyHistoryDto {

    private Integer goodsPrice;
    private Integer goodsBuyCount;
    private Integer deliveryFee;
    private Integer realDeliveryFee;
    private Integer totalFee;
    private Long boardId;

    @Builder
    public EndBuyHistoryDto(Integer goodsPrice, Integer goodsBuyCount, Integer deliveryFee, Integer realDeliveryFee, Integer totalFee, Long boardId) {
        this.goodsPrice = goodsPrice;
        this.goodsBuyCount = goodsBuyCount;
        this.deliveryFee = deliveryFee;
        this.realDeliveryFee = realDeliveryFee;
        this.totalFee = totalFee;
        this.boardId = boardId;
    }
}
