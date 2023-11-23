package com.jigume.domain.board.exception.exception;

import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ResourceNotFoundException;

import static com.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;

public class BoardNotFoundException extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public BoardNotFoundException() {
        super(BOARD_NOT_FOUND);
        this.exceptionCode = BOARD_NOT_FOUND;
    }
}
