package com.jigume.domain.board.exception.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BoardExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity commentNotFoundException(
            CommentNotFoundException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getExceptionCode().getHttpStatus()
        );
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity boardNotFoundException(
            BoardNotFoundException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
