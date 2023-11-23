package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.*;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.entity.Comment;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.board.repository.CommentRepository;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.domain.member.service.MemberService;
import com.jigume.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ResourceNotFoundException());

        Comment comment = Comment.createReplyComment(member, board, parentComment
                , createReplyComment.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long boardId, Long commentId, UpdateCommentDto updateCommentDto) {
        String commentContent = updateCommentDto.getCommentContent();

        getBoard(boardId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException());

        Member member = memberService.getMember();

        if (!comment.getMember().equals(member)) {
            throw new AuthNotAuthorizationMemberException();
        }

        comment.updateContent(commentContent);
    }

    public GetCommentsDto getComments(Long boardId) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);
        Goods goods = board.getGoods();

        isOrderSell(goods, member);

        List<Comment> commentList = board.getCommentList();

        List<CommentDto> commentDtoList = commentList.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());

        return new GetCommentsDto(commentDtoList);
    }

    private void isOrderSell(Goods goods, Member member) {
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthNotAuthorizationMemberException();
        }
    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardIdWithGetComment(boardId)
                .orElseThrow(() -> new ResourceNotFoundException());
        return board;
    }

    private CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto().builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .memberNickname(comment.getMember().getNickname())
                .created_at(comment.getCreatedDate())
                .build();

        return commentDto;
    }
}
