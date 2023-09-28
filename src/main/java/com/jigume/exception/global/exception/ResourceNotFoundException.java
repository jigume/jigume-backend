package com.jigume.exception.global.exception;

import com.jigume.exception.global.ExceptionCode;
import jakarta.persistence.EntityNotFoundException;


public class ResourceNotFoundException extends EntityNotFoundException {

    private final ExceptionCode exceptionCode;

    public ResourceNotFoundException(
            ExceptionCode exceptionCode
    ) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
