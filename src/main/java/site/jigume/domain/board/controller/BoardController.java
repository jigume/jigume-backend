package site.jigume.domain.board.controller;

import site.jigume.domain.board.dto.BoardCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.board.dto.BoardDto;
import site.jigume.domain.board.dto.BoardUpdateDto;
import site.jigume.domain.board.exception.exception.BoardException;
import site.jigume.domain.board.service.BoardService;
import site.jigume.domain.member.exception.auth.AuthException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/goods/{goodsId}/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @Operation(summary = "게시판 내용을 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판을 가져온다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))),
            @ApiResponse(responseCode = "401", description = "게시판 권한이 없음. (판매자나 주문자가 아님)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
    })
    @PostMapping
    public ResponseEntity saveBoard(@PathVariable("goodsId") Long goodsId,
                                    @RequestBody BoardCreateDto boardCreateDto) {
        Long boardId = boardService.save(goodsId, boardCreateDto);

        return new ResponseEntity(boardId, OK);
    }

    @Operation(summary = "게시판을 가져온다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판을 가져온다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))),
            @ApiResponse(responseCode = "401", description = "게시판 권한이 없음. (판매자나 주문자가 아님)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long goodsId, @PathVariable Long boardId) {
        BoardDto board = boardService.getBoard(goodsId, boardId);

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
    public ResponseEntity updateBoardContent(@PathVariable Long goodsId, @PathVariable Long boardId,
                                             @RequestBody BoardUpdateDto boardUpdateDto) {
        BoardDto boardDto = boardService.updateBoard(goodsId, boardId, boardUpdateDto);

        return new ResponseEntity(boardDto, OK);
    }
}
