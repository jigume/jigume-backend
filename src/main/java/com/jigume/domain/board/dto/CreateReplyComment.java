package com.jigume.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateReplyComment {

    private Long parentCommentId;
    private String content;
}
