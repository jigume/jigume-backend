package com.jigume.domain.board.repository;

import com.jigume.domain.board.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByBoardId(Long boardId, Pageable pageable);
}