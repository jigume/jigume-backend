package com.jigume.global.exception;

import jakarta.persistence.EntityNotFoundException;

import static com.jigume.global.exception.GlobalErrorCode.*;


public class ResourceNotFoundException extends EntityNotFoundException {

    private final ExceptionCode exceptionCode;

    public ResourceNotFoundException() {
        super(RESOURCE_NOT_FOUND.getMessage());
        this.exceptionCode = RESOURCE_NOT_FOUND;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
