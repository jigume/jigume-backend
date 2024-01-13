package site.jigume.domain.goods.exception;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class GoodsException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public GoodsException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
