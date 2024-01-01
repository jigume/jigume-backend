package com.jigume.domain.goods.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class GoodsException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public GoodsException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
