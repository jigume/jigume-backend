package com.jigume.domain.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long commentId;
    private String content;
    private String memberNickname;
    private LocalDateTime created_at;

    @Builder
    public CommentDto(Long commentId, String content, String memberNickname, LocalDateTime created_at) {
        this.commentId = commentId;
        this.content = content;
        this.memberNickname = memberNickname;
        this.created_at = created_at;
    }
}
