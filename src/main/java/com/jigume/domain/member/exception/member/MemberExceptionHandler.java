package com.jigume.domain.member.exception.member;

import com.jigume.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class MemberExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity memberException(
            MemberException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exception.getExceptionCode()),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
