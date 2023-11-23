package com.jigume.domain.board.exception.exception;

import com.jigume.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum BoardExceptionCode implements ExceptionCode {
    BOARD_NOT_FOUND(NOT_FOUND, "BOARD-C-001", "게시판을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "COMMENT-C-001", "댓글을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
