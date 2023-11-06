package com.jigume.global.aws.s3.exception;

import com.jigume.global.aws.s3.exception.exception.S3InvalidImageException;
import com.jigume.global.exception.ExceptionCode;
import com.jigume.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class S3ExceptionHandler {

    @ExceptionHandler(S3InvalidImageException.class)
    public ResponseEntity<ExceptionResponse> s3InvalidImageException(
            S3InvalidImageException e
    ){
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}
