package com.jigume.domain.order.exception.sell;

import com.jigume.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SellExceptionHandler {

    @ExceptionHandler(value = SellException.class)
    public ResponseEntity handleSellException(SellException exception) {
        ExceptionCode exceptionCode = exception.getExceptionCode();
        log.error("Sell Exception: {}", exceptionCode.getMessage());

        return new ResponseEntity<> (
                exceptionCode.getMessage(),
                exceptionCode.getHttpStatus()
        );
    }
}
