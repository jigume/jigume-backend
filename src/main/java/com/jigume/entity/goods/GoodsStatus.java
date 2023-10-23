package com.jigume.entity.goods;

import com.jigume.exception.global.GlobalErrorCode;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GoodsStatus {
    PROCESSING(0), END(1);

    private final Integer status;

    GoodsStatus(Integer status) {
        this.status = status;
    }

    public static GoodsStatus getGoodsStatus(Integer status) {
        return Arrays.stream(GoodsStatus.values()).filter(goodsStatus -> goodsStatus.getStatus() == status)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }
}