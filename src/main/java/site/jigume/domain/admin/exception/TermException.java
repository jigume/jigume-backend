package site.jigume.domain.admin.exception;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class TermException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public TermException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
