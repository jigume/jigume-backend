package com.jigume.domain.member.exception.auth.exception;

import com.jigume.global.exception.ExceptionCode;
import org.springframework.security.core.AuthenticationException;

import static com.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

public class AuthNotAuthorizationMemberException extends AuthenticationException {

    private final ExceptionCode exceptionCode;

    public AuthNotAuthorizationMemberException() {
        super(NOT_AUTHORIZATION_USER.getCode());
        this.exceptionCode = NOT_AUTHORIZATION_USER;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
