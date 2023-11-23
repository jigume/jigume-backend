package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ResourceNotFoundException;

import static com.jigume.domain.member.exception.member.MemberException.MEMBER_NOT_FOUND;

public class LoginMemberException extends ResourceNotFoundException{

    private final ExceptionCode exceptionCode;

    public LoginMemberException() {
        super(MEMBER_NOT_FOUND);
        this.exceptionCode = MEMBER_NOT_FOUND;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
