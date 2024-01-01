package com.jigume.domain.order.exception.order;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum OrderExceptionCode implements ExceptionCode {

    ORDER_OVER_COUNT(BAD_REQUEST, "O-C-001", "주문할 수 있는 수량에서 초과하였습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "O-C-002", "");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
