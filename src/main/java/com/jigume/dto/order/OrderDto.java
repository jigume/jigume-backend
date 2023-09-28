package com.jigume.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Integer orderCount;

    private Integer orderType;

    @Builder
    public OrderDto(Integer orderCount, Integer orderType) {
        this.orderCount = orderCount;
        this.orderType = orderType;
    }
}
