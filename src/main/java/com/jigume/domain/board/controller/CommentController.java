package com.jigume.domain.board.controller;

import com.jigume.domain.board.dto.CreateCommentDto;
import com.jigume.domain.board.dto.CreateReplyComment;
import com.jigume.domain.board.dto.UpdateCommentDto;
import com.jigume.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment/new")
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

    @PostMapping("/{boardId}/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long boardId,
                                        @PathVariable Long commentId,
                                        @RequestBody UpdateCommentDto updateCommentDto) {
        commentService.updateComment(boardId, commentId, updateCommentDto);

        return new ResponseEntity("댓글이 성공적으로 저장되었습니다.", OK);
    }
}
