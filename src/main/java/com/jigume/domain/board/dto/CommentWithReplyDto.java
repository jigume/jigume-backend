package com.jigume.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CommentWithReplyDto {

    private CommentDto parent;

    private List<CommentDto> reply;

    public CommentWithReplyDto(CommentDto parent, List<CommentDto> reply) {
        this.parent = parent;
        this.reply = reply;
    }
}
