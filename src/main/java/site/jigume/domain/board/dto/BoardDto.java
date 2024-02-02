package site.jigume.domain.board.dto;

import site.jigume.domain.board.entity.Board;

import java.time.LocalDateTime;

public record BoardDto(String title, String content, String hostName,
                       LocalDateTime created_at, LocalDateTime modified_at) {

    public static BoardDto from(Board board) {
        return new BoardDto(board.getTitle(), board.getContent(),
                board.getGoods().getSell().getMember().getNickname(),
                board.getCreatedDate(), board.getModifiedDate());
    }
}
