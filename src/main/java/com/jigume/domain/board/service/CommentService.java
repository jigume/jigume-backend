package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.CommentDto;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.entity.Comment;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.board.repository.CommentRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.global.exception.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void createComment(String memberIdx, Long boardId, CommentDto commentDto) {
        Member member = memberService.getMember();
        Board board = boardRepository.findBoardByBoardIdWithGetComment(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        Comment comment = Comment.createComment(member, board,commentDto.getContent());
        board.addComment(comment);
        commentRepository.save(comment);
    }


}
