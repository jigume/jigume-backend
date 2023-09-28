package com.jigume.entity.order;

import com.jigume.exception.global.GlobalErrorCode;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    PROCESSING(0), END(1);

    private final Integer status;

    OrderStatus(Integer status) {
        this.status = status;
    }

    public static OrderStatus getOrderStatus(Integer status) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.getStatus() == status)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }
}
