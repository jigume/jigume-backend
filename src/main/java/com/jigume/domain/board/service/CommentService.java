package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.*;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.entity.Comment;
import com.jigume.domain.board.exception.exception.BoardException;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.board.repository.CommentRepository;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.AuthException;
import com.jigume.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;
import static com.jigume.domain.board.exception.exception.BoardExceptionCode.COMMENT_NOT_FOUND;
import static com.jigume.domain.member.exception.auth.AuthExceptionCode.AUTHENTICATION_ERROR;
import static com.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(Long boardId, CreateCommentDto commentDto) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);

        isOrderSell(board.getGoods(), member);

        Comment comment = Comment.createComment(member, board, commentDto.getContent());
        board.addComment(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void createReplyComment(Long boardId, CreateReplyComment createReplyComment) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);

        isOrderSell(board.getGoods(), member);

        Comment parentComment = commentRepository.findById(createReplyComment.getParentCommentId())
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        Comment comment = Comment.createReplyComment(member, board, parentComment
                , createReplyComment.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long boardId, Long commentId, UpdateCommentDto updateCommentDto) {
        String commentContent = updateCommentDto.getCommentContent();

        getBoard(boardId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        Member member = memberService.getMember();

        if (!comment.getMember().equals(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        comment.updateContent(commentContent);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Member member = memberService.getMember();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        if (comment.getMember().equals(member)) {
            throw new AuthException(AUTHENTICATION_ERROR);
        }

        comment.deleteComment();
    }

    @Transactional(readOnly = true)
    public GetCommentsDto getComments(Long boardId, Pageable pageable) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);
        Goods goods = board.getGoods();

        isOrderSell(goods, member);

        Page<Comment> commentsByBoardId = commentRepository.findCommentsByBoardId(boardId, pageable);

        int totalPages = commentsByBoardId.getTotalPages();

        List<CommentWithReplyDto> commentWithReplyDtoList = commentsByBoardId.stream()
                .map(comment -> {
                    List<CommentDto> childList = comment.getChildren().stream()
                            .map(this::toCommentDto)
                            .collect(Collectors.toList());
                    CommentDto parent = toCommentDto(comment);
                    return new CommentWithReplyDto(parent, childList);
                })
                .collect(Collectors.toList());


        return new GetCommentsDto(commentWithReplyDtoList, totalPages);
    }

    private void isOrderSell(Goods goods, Member member) {
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }
    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        return board;
    }

    private CommentDto toCommentDto(Comment comment) {
        if (!comment.getIsDelete()) {
            return new CommentDto().builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .memberNickname(comment.getMember().getNickname())
                    .created_at(comment.getCreatedDate())
                    .modified_at(comment.getModifiedDate())
                    .isDelete(comment.getIsDelete())
                    .build();
        }

        return new CommentDto().builder().
                isDelete(comment.getIsDelete())
                .commentId(comment.getId())
                .memberNickname(comment.getMember().getNickname())
                .created_at(comment.getCreatedDate())
                .modified_at(comment.getModifiedDate())
                .build();
    }
}
