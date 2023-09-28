package com.jigume.exception.member;

import com.jigume.exception.global.ExceptionCode;
import com.jigume.exception.global.exception.ResourceNotFoundException;

import static com.jigume.exception.member.MemberException.MEMBER_NOT_FOUND;

public class LoginMemberException extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public LoginMemberException() {
        super(MEMBER_NOT_FOUND);
        this.exceptionCode = MEMBER_NOT_FOUND;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
