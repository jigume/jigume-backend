package com.jigume.domain.order.exception.order;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
