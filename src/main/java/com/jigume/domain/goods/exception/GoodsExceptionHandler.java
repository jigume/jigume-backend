package com.jigume.domain.goods.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GoodsExceptionHandler {

    @ExceptionHandler(GoodsNotFoundException.class)
    public ResponseEntity goodsNotFoundException(
            GoodsNotFoundException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
