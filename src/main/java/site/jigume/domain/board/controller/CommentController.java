package site.jigume.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.board.dto.CreateCommentDto;
import site.jigume.domain.board.dto.CreateReplyComment;
import site.jigume.domain.board.dto.GetCommentsDto;
import site.jigume.domain.board.dto.UpdateCommentDto;
import site.jigume.domain.board.exception.exception.BoardException;
import site.jigume.domain.board.service.CommentService;
import site.jigume.domain.member.exception.auth.AuthException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 저장된다."),
            @ApiResponse(responseCode = "401", description = "댓글을 작성할 권한이 없음. (판매자나 주문자가 아님)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @PostMapping("/{boardId}/comment")
    public ResponseEntity createComment(@PathVariable Long boardId,
                                        @RequestBody CreateCommentDto commentDto) {
        commentService.createComment(boardId, commentDto);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "답글을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답글이 성공적으로 저장된다."),
            @ApiResponse(responseCode = "401", description = "답글을 작성할 권한이 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class))),
            @ApiResponse(responseCode = "404", description = "답글을 달 댓글을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @PostMapping("/{boardId}/comment/reply")
    public ResponseEntity createReplyComment(@PathVariable Long boardId,
                                             @RequestBody CreateReplyComment createReplyComment) {
        commentService.createReplyComment(boardId, createReplyComment);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "댓글을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 수정된다."),
            @ApiResponse(responseCode = "401", description = "댓글을 수정할 권한이 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class))),
            @ApiResponse(responseCode = "404", description = "수정할 댓글을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @PostMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long boardId,
                                        @PathVariable Long commentId,
                                        @RequestBody UpdateCommentDto updateCommentDto) {
        commentService.updateComment(boardId, commentId, updateCommentDto);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "댓글을 가져온다. 삭제된 댓글일 경우 댓글 내용은 보내지 않고 나머지를 보낸다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글을 성공적으로 가져온다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetCommentsDto.class))),
            @ApiResponse(responseCode = "401", description = "댓글을 가져올 권한이 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class))),
    })
    @GetMapping("/{boardId}/comment")
    public ResponseEntity getComment(@PathVariable Long boardId,
                                     Pageable pageable) {
        GetCommentsDto comments = commentService.getComments(boardId, pageable);

        return new ResponseEntity(comments, OK);
    }

    @Operation(summary = "댓글을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 삭제된다."),
            @ApiResponse(responseCode = "401", description = "댓글을 삭제할 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "보드를 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class))),
            @ApiResponse(responseCode = "404", description = "삭제할 댓글을 찾을 수 없음.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BoardException.class)))
    })
    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long boardId,
                                        @PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity(OK);
    }
}
