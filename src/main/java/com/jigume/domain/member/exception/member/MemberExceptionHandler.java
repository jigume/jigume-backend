package com.jigume.domain.member.exception.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class MemberExceptionHandler {

    @ExceptionHandler(LoginMemberException.class)
    public ResponseEntity failLoginException(
            LoginMemberException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                exception.getMessage(),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
