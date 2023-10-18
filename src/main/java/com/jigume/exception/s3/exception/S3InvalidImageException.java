package com.jigume.exception.s3.exception;

import com.jigume.exception.global.ExceptionCode;

import static com.jigume.exception.s3.S3ErrorCode.INVALID_S3_IMAGE;

public class S3InvalidImageException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public S3InvalidImageException(){
        super(INVALID_S3_IMAGE.getMessage());
        this.exceptionCode = INVALID_S3_IMAGE;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}