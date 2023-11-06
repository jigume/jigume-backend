package com.jigume.domain.order.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class OrderExceptionHandler {

    @ExceptionHandler(OrderOverCountException.class)
    public ResponseEntity orderOverCountException(
            OrderOverCountException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
