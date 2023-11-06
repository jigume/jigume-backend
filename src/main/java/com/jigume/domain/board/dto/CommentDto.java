package com.jigume.domain.board.dto;

import com.jigume.domain.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String content;
    private String memberNickname;
    private LocalDateTime created_at;

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.content = comment.getContent();
        commentDto.memberNickname = comment.getMember().getNickname();
        commentDto.created_at = comment.getCreatedDate();

        return commentDto;
    }
}
