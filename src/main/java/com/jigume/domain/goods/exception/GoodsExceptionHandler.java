package com.jigume.domain.goods.exception;

import com.jigume.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GoodsExceptionHandler {

    @ExceptionHandler(GoodsException.class)
    public ResponseEntity<ExceptionResponse> goodsNotFoundException(
            GoodsException exception
    ) {
        log.error("{handle GoodsException} - {}", exception.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exception.getExceptionCode()),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
