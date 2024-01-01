package com.jigume.domain.member.exception.auth;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final ExceptionCode exceptionCode;


    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
