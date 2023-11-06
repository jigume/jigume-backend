package com.jigume.domain.member.exception.auth.exception;

import com.jigume.global.exception.ExceptionCode;
import org.springframework.security.core.AuthenticationException;

import static com.jigume.domain.member.exception.auth.AuthExceptionCode.INVALID_REFRESH_TOKEN;


public class AuthInvalidRefreshToken extends AuthenticationException {
    private final ExceptionCode exceptionCode;

    public AuthInvalidRefreshToken() {
        super(INVALID_REFRESH_TOKEN.getCode());
        this.exceptionCode = INVALID_REFRESH_TOKEN;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
