package com.jigume.domain.goods.exception;

import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ResourceNotFoundException;

import static com.jigume.domain.goods.exception.GoodsExceptionCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
        this.exceptionCode = CATEGORY_NOT_FOUND;
    }
}
