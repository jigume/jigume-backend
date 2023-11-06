package com.jigume.domain.member.exception.auth.exception;

import com.jigume.global.exception.ExceptionCode;
import org.springframework.security.core.AuthenticationException;

import static com.jigume.domain.member.exception.auth.AuthExceptionCode.INVALID_TOKEN;


public class AuthInvalidTokenException extends AuthenticationException {
    private final ExceptionCode exceptionCode;
    public AuthInvalidTokenException() {
        super(INVALID_TOKEN.getCode());
        this.exceptionCode = INVALID_TOKEN;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

}
