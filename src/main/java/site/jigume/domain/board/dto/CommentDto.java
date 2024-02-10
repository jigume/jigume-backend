package site.jigume.domain.board.dto;

import site.jigume.domain.board.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(Long commentId, String content, String memberNickname,
                         LocalDateTime created_at, LocalDateTime modified_at,
                         Boolean isDelete) {

    public static CommentDto from(Comment comment) {
        if (!comment.isDelete()) {
            return new CommentDto(
                    comment.getId()
                    , comment.getContent()
                    , comment.getMember().getNickname()
                    , comment.getCreatedDate()
                    , comment.getModifiedDate()
                    , comment.isDelete());
        }

        return new CommentDto(
                comment.getId(),
                null,
                comment.getMember().getNickname(),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                comment.isDelete()
        );
    }
}
