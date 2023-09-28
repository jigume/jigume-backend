package com.jigume.controller;

import com.jigume.dto.board.BoardDto;
import com.jigume.dto.board.CommentDto;
import com.jigume.service.board.BoardService;
import com.jigume.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.getBoard(boardId);

        return new ResponseEntity<>(board, OK);
    }

    @PostMapping("/{boardId}/comment/new")
    public ResponseEntity createComment(@PathVariable Long boardId, @RequestParam("memberIdx") String memberIdx,
                                        @RequestBody CommentDto commentDto) {
        commentService.createComment(memberIdx, boardId, commentDto);

        return new ResponseEntity("댓글이 성공적으로 저장되었습니다.", OK);
    }
}
