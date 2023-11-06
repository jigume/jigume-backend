package com.jigume.domain.member.exception.auth.exception;


import com.jigume.global.exception.ExceptionCode;

import static com.jigume.domain.member.exception.auth.AuthExceptionCode.INVALID_AUTHORIZATION_CODE;

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
