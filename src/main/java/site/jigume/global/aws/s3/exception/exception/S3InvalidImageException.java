package site.jigume.global.aws.s3.exception.exception;

import site.jigume.global.aws.s3.exception.S3ErrorCode;
import site.jigume.global.exception.ExceptionCode;

public class S3InvalidImageException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public S3InvalidImageException(){
        super(S3ErrorCode.INVALID_S3_IMAGE.getMessage());
        this.exceptionCode = S3ErrorCode.INVALID_S3_IMAGE;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
