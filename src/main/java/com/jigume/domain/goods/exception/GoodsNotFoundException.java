package com.jigume.domain.goods.exception;

import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ResourceNotFoundException;

import static com.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

public class GoodsNotFoundException extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public GoodsNotFoundException() {
        super(GOODS_NOT_FOUND);
        this.exceptionCode = GOODS_NOT_FOUND;
    }
}
