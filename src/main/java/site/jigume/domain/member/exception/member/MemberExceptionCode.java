package site.jigume.domain.member.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.jigume.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {

    MEMBER_NOT_FOUND(NOT_FOUND, "M-C-001", "회원을 찾을 수 없습니다."),
    MEMBER_DUPLICATE_ERROR(BAD_REQUEST, "M-C-002", "중복된 닉네임입니다."),
    MEMBER_INVALID_NICKNAME(BAD_REQUEST, "M-C-003", "유효하지 않은 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
