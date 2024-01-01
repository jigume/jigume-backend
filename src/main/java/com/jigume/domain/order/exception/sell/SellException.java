package com.jigume.domain.order.exception.sell;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public SellException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
