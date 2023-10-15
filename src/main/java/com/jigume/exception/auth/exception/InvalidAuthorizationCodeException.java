package com.jigume.exception.auth.exception;


import com.jigume.exception.global.ExceptionCode;

import static com.jigume.exception.auth.AuthExceptionCode.INVALID_AUTHORIZATION_CODE;

public class InvalidAuthorizationCodeException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public InvalidAuthorizationCodeException() {
        super(INVALID_AUTHORIZATION_CODE.getMessage());
        this.exceptionCode = INVALID_AUTHORIZATION_CODE;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
