package site.jigume.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByBoardId(Long boardId, Pageable pageable);

//    void deleteComment(@Param("boardId") Long boardId);
}