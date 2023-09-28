package com.jigume.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoneDto {

    private Integer goodsPrice;

    private Integer goodsDeliveryFee;

    private Integer realDeliveryFee;

    private Integer totalFee;

    public static DoneDto toDoneDto(Integer goodsPrice, Integer goodsDeliveryFee,
                                    Integer orderCount, Integer orderCase) {
        DoneDto doneDto = new DoneDto();
        doneDto.goodsPrice = goodsPrice;
        doneDto.goodsDeliveryFee = goodsDeliveryFee;
        doneDto.realDeliveryFee = goodsDeliveryFee / orderCase;
        doneDto.totalFee = goodsPrice * orderCount + doneDto.realDeliveryFee;

        return doneDto;
    }
}
