package com.jigume.dto.board;

import com.jigume.entity.board.Board;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardDto {

    private String boardName;
    private String boardContent;
    private String hostName;
    private LocalDateTime created_at;

    private List<CommentDto> commentDtoList = new ArrayList<>();

    public static BoardDto toBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.boardName = board.getBoardName();
        boardDto.hostName = board.getGoods().getSell().getMember().getNickname();
        boardDto.boardContent = board.getBoardContent();
        boardDto.created_at = board.getCreatedDate();

        boardDto.commentDtoList = board.getCommentList().stream()
                .map(CommentDto::toCommentDto).collect(Collectors.toList());

        return boardDto;
    }
}
