package site.jigume.domain.order.exception.order;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class OrderException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
