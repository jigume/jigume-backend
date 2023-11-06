package com.jigume.global.aws.s3.exception.exception;

import com.jigume.global.exception.ExceptionCode;

import static com.jigume.global.aws.s3.exception.S3ErrorCode.INVALID_S3_IMAGE;

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
