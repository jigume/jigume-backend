package com.jigume.domain.board.controller;

import com.jigume.domain.board.dto.BoardDto;
import com.jigume.domain.board.dto.UpdateBoardDto;
import com.jigume.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.getBoard(boardId);

        return new ResponseEntity<>(board, OK);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity updateBoardContent(@PathVariable Long boardId, @RequestBody UpdateBoardDto updateBoardDto) {
        BoardDto boardDto = boardService.updateBoard(boardId, updateBoardDto);

        return new ResponseEntity(boardDto, OK);
    }
}
