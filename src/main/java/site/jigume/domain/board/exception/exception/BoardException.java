package site.jigume.domain.board.exception.exception;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class BoardException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public BoardException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
