package site.jigume.domain.order.exception.sell;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.jigume.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum SellExceptionCode implements ExceptionCode {

    SELL_INVALID_AUTH(UNAUTHORIZED, "S-C-001", "판매 내역을 조작할 권한이 없습니다."),
    SELL_NOT_FOUND(NOT_FOUND, "S-C-002", "판매 내역을 찾을 수 없습니다."),
    SELL_BAD_REQUEST(BAD_REQUEST, "S-C-003", "잘못된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
