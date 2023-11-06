package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberException implements ExceptionCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M-C-001", "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
