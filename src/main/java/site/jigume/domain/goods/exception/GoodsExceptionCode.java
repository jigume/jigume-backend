package site.jigume.domain.goods.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.jigume.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum GoodsExceptionCode implements ExceptionCode {
    GOODS_NOT_FOUND(NOT_FOUND, "GOODS-C-001", "상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "CATEGORY-C-001", "카테고리를 찾을 수 없습니다."),
    GOODS_DELETE_IMPOSSIBLE(BAD_REQUEST, "GOODS-C-002", "상품을 삭제할 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
