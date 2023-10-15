package com.jigume.exception.auth;


import com.jigume.exception.auth.exception.*;
import com.jigume.exception.global.ExceptionCode;
import com.jigume.exception.global.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    @ExceptionHandler(AuthInvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> authInvalidTokenException(
            AuthInvalidTokenException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(AuthExpiredTokenException.class)
    public ResponseEntity<ExceptionResponse> authExpiredTokenException(
            AuthExpiredTokenException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(AuthInvalidRefreshToken.class)
    public ResponseEntity<ExceptionResponse> authInvalidRefreshToken(
            AuthInvalidRefreshToken e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(AuthTokenNotFoundException.class)
    public ResponseEntity<ExceptionResponse> authNoRefreshToken(
            AuthTokenNotFoundException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(InvalidAuthorizationCodeException.class)
    public ResponseEntity<ExceptionResponse> InvalidAuthorizationCodeException(
            InvalidAuthorizationCodeException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", exceptionCode.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

}
