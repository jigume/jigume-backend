package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionCode;
import org.springframework.dao.DuplicateKeyException;

import static com.jigume.domain.member.exception.member.MemberException.MEMBER_DUPLICATE_ERROR;

public class DuplicateNicknameException extends DuplicateKeyException {
    private final ExceptionCode exceptionCode;

    public DuplicateNicknameException() {
        super(MEMBER_DUPLICATE_ERROR.getMessage());
        this.exceptionCode = MEMBER_DUPLICATE_ERROR;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
