package site.jigume.domain.order.exception.sell;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class SellException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public SellException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
