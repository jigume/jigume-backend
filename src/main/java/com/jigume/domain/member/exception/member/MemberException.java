package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public MemberException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
