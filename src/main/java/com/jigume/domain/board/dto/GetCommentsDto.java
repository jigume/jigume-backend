package com.jigume.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GetCommentsDto {

    List<CommentDto> commentDtoList;


    public GetCommentsDto(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
