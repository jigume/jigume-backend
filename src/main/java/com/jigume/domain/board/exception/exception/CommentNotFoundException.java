package com.jigume.domain.board.exception.exception;

import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ResourceNotFoundException;

import static com.jigume.domain.board.exception.exception.BoardExceptionCode.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND);
        this.exceptionCode = COMMENT_NOT_FOUND;
    }
}
