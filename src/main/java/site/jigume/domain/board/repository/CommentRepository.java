package site.jigume.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "join fetch c.member join fetch c.member.file " +
            "where c.board.id = :boardId")
    Page<Comment> findCommentsByBoardId(@Param("boardId") Long boardId, Pageable pageable);

//    void deleteComment(@Param("boardId") Long boardId);
}