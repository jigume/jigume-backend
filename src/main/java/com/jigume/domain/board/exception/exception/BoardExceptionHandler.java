package com.jigume.domain.board.exception.exception;

import com.jigume.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BoardExceptionHandler {

    @ExceptionHandler(BoardException.class)
    public ResponseEntity boardException(
            BoardException exception
    ) {
        log.error("{handle BoardException} - {}", exception.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exception.getExceptionCode()),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
