package com.jigume.domain.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDto {

    private String boardName;
    private String boardContent;
    private String hostName;
    private LocalDateTime created_at;
    private GetCommentsDto commentsDto;

    @Builder
    public BoardDto(String boardName, String boardContent, String hostName, LocalDateTime created_at, GetCommentsDto comments) {
        this.boardName = boardName;
        this.boardContent = boardContent;
        this.hostName = hostName;
        this.created_at = created_at;
        this.commentsDto = comments;
    }
}
