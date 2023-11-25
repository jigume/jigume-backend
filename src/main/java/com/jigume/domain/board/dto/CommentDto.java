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
    private LocalDateTime modified_at;
    private Boolean isDelete;

    @Builder
    public CommentDto(Long commentId, String content, String memberNickname,
                      LocalDateTime created_at, LocalDateTime modified_at, Boolean isDelete) {
        this.commentId = commentId;
        this.content = content;
        this.memberNickname = memberNickname;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.isDelete = isDelete;
    }
}
