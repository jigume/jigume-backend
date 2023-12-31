package com.jigume.domain.goods.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum GoodsExceptionCode implements ExceptionCode {
    GOODS_NOT_FOUND(NOT_FOUND, "GOODS-C-001", "상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "CATEGORY-C-001", "카테고리를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
