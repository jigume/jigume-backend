package site.jigume.domain.board.dto;

import site.jigume.domain.board.entity.Board;
import site.jigume.domain.goods.entity.Goods;

import java.time.LocalDateTime;

public record BoardDto(String title, String content, String hostName,
                       LocalDateTime created_at, LocalDateTime modified_at) {

    public static BoardDto from(Board board, Goods goods) {
        return new BoardDto(board.getTitle(), board.getContent(),
                goods.getSell().getMember().getNickname(),
                board.getCreatedDate(), board.getModifiedDate());
    }
}
