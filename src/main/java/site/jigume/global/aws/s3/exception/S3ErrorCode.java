package site.jigume.global.aws.s3.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.jigume.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ExceptionCode {
    INVALID_S3_IMAGE(BAD_REQUEST, "S3-C-001", "유효하지 않은 이미지입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
