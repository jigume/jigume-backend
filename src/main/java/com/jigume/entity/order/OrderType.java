package com.jigume.entity.order;

import com.jigume.exception.global.GlobalErrorCode;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderType {
    BUY(0), SELL(1);

    private final Integer type;

    OrderType(Integer type) {
        this.type = type;
    }

    public static OrderType getOrderType(Integer type) {
        return Arrays.stream(OrderType.values()).filter(orderType -> orderType.getType() == type)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }
}
