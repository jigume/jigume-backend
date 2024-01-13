package site.jigume.global.exception;


import static site.jigume.global.exception.GlobalErrorCode.SERVER_ERROR;

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
