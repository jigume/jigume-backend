package com.jigume.domain.board.exception.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public BoardException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
