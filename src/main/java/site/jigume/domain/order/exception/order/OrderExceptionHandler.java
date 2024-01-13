package site.jigume.domain.order.exception.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.jigume.global.exception.ExceptionResponse;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity orderException(
            OrderException exception
    ) {
        log.error("{}", exception.getMessage());

        return new ResponseEntity<>(
                new ExceptionResponse(exception.getExceptionCode()),
                exception.getExceptionCode().getHttpStatus()
        );
    }
}
