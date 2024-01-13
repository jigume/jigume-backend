package site.jigume.domain.member.exception.auth;

import lombok.Getter;
import site.jigume.global.exception.ExceptionCode;

@Getter
public class AuthException extends RuntimeException {

    private final ExceptionCode exceptionCode;


    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
