package site.jigume.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetCommentsDto {

    private List<CommentWithReplyDto> commentDtoList;
    private int totalPages;
}
