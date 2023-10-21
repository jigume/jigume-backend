package com.jigume.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long goodsId;

    private Integer orderGoodsCount;

    public OrderDto(Long goodsId, Integer orderGoodsCount) {
        this.goodsId = goodsId;
        this.orderGoodsCount = orderGoodsCount;
    }
}
