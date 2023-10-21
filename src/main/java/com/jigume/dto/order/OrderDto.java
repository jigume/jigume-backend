package com.jigume.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Integer orderGoodsCount;

    private Integer orderType;

    @Builder
    public OrderDto(Integer orderGoodsCount, Integer orderType) {
        this.orderGoodsCount = orderGoodsCount;
        this.orderType = orderType;
    }
}
