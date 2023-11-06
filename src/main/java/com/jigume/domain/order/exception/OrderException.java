package com.jigume.domain.order.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum OrderException implements ExceptionCode {

    ORDER_OVER_COUNT(BAD_REQUEST, "O-C-001", "주문할 수 있는 수량에서 초과하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
