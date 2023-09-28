package com.jigume.exception.global.exception;


import com.jigume.exception.global.ExceptionCode;

import static com.jigume.exception.global.GlobalErrorCode.SERVER_ERROR;

public class GlobalServerErrorException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public GlobalServerErrorException() {

        super(SERVER_ERROR.getMessage());
        this.exceptionCode = SERVER_ERROR;
    }

    public ExceptionCode getExceptionCode() {

        return exceptionCode;
    }
}
