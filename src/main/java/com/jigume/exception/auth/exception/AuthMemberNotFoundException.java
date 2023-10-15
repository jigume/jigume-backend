package com.jigume.exception.auth.exception;

import com.jigume.exception.global.ExceptionCode;
import org.springframework.security.core.AuthenticationException;

import static com.jigume.exception.auth.AuthExceptionCode.AUTH_MEMBER_NOT_FOUND;

public class AuthMemberNotFoundException extends AuthenticationException {
    private final ExceptionCode exceptionCode;

    public AuthMemberNotFoundException() {
        super(AUTH_MEMBER_NOT_FOUND.getCode());
        this.exceptionCode = AUTH_MEMBER_NOT_FOUND;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
