package site.jigume.domain.member.exception.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.jigume.global.exception.ExceptionResponse;

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
