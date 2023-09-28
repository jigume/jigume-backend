package com.jigume.exception.order;

import com.jigume.exception.global.ExceptionCode;
import lombok.Getter;

import static com.jigume.exception.order.OrderException.ORDER_OVER_COUNT;

@Getter
public class OrderOverCountException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderOverCountException() {
        super(ORDER_OVER_COUNT.getMessage());
        this.exceptionCode = ORDER_OVER_COUNT;
    }

}
