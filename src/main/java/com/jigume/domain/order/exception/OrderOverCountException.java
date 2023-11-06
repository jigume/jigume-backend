package com.jigume.domain.order.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

import static com.jigume.domain.order.exception.OrderException.ORDER_OVER_COUNT;

@Getter
public class OrderOverCountException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderOverCountException() {
        super(ORDER_OVER_COUNT.getMessage());
        this.exceptionCode = ORDER_OVER_COUNT;
    }

}
