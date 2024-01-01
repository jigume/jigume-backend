package com.jigume.domain.board.controller;

import com.jigume.domain.board.dto.BoardDto;
import com.jigume.domain.board.dto.UpdateBoardDto;
import com.jigume.domain.board.exception.exception.BoardException;
import com.jigume.domain.board.service.BoardService;
import com.jigume.domain.member.exception.auth.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판을 가져온다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판을 가져온다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))),
            @ApiResponse(responseCode = "401", description = "게시판 권한이 없음. (판매자나 주문자가 아님)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.getBoard(boardId);

        return new ResponseEntity<>(board, OK);
    }

    @Operation(summary = "게시판 본문 내용을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판을 가져온다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))),
            @ApiResponse(responseCode = "401", description = "게시판 권한이 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @PostMapping("/{boardId}")
    public ResponseEntity updateBoardContent(@PathVariable Long boardId, @RequestBody UpdateBoardDto updateBoardDto) {
        BoardDto boardDto = boardService.updateBoard(boardId, updateBoardDto);

        return new ResponseEntity(boardDto, OK);
    }
}
