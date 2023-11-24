package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionCode;

import static com.jigume.domain.member.exception.member.MemberException.MEMBER_INVALID_NICKNAME;

public class InvalidNicknameException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public InvalidNicknameException() {
        super(MEMBER_INVALID_NICKNAME.getMessage());
        this.exceptionCode = MEMBER_INVALID_NICKNAME;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
