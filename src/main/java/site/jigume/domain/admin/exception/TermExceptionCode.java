package site.jigume.domain.admin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.jigume.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum TermExceptionCode implements ExceptionCode {

    TERM_NOT_FOUND(NOT_FOUND, "TE-C-001", "약관을 찾을 수 없습니다."),
    TERM_REQUIRE_ERROR(BAD_REQUEST, "TE-C-002", "필수 약관은 반드시 동의해야 합니다."),
    TERM_DUPLICATED_ERROR(BAD_REQUEST, "TE-C-003", "약관은 중복 될 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
