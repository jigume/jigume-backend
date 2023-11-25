package com.jigume.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GetCommentsDto {

    List<CommentWithReplyDto> commentDtoList;


    public GetCommentsDto(List<CommentWithReplyDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
