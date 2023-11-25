package com.jigume.domain.board.controller;

import com.jigume.domain.board.dto.*;
import com.jigume.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    public ResponseEntity createComment(@PathVariable Long boardId,
                                        @RequestBody CreateCommentDto commentDto) {
        commentService.createComment(boardId, commentDto);

        return new ResponseEntity("댓글이 성공적으로 저장되었습니다.", OK);
    }

    @PostMapping("/{boardId}/comment/reply")
    public ResponseEntity createReplyComment(@PathVariable Long boardId,
                                             @RequestBody CreateReplyComment createReplyComment) {
        commentService.createReplyComment(boardId, createReplyComment);

        return new ResponseEntity("댓글이 성공적으로 저장되었습니다.", OK);
    }

    @PostMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long boardId,
                                        @PathVariable Long commentId,
                                        @RequestBody UpdateCommentDto updateCommentDto) {
        commentService.updateComment(boardId, commentId, updateCommentDto);

        return new ResponseEntity("댓글이 성공적으로 저장되었습니다.", OK);
    }

    @GetMapping("/{boardId}/comment")
    public ResponseEntity getComment(@PathVariable Long boardId,
                                     Pageable pageable) {
        GetCommentsDto comments = commentService.getComments(boardId, pageable);

        return new ResponseEntity(comments, OK);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long boardId,
                                        @PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity(OK);
    }
}
