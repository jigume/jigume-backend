package site.jigume.domain.member.exception.member;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class MemberException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public MemberException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
