package com.jigume.service.board;

import com.jigume.dto.board.CommentDto;
import com.jigume.entity.member.Member;
import com.jigume.entity.board.Board;
import com.jigume.entity.board.Comment;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.BoardRepository;
import com.jigume.repository.CommentRepository;
import com.jigume.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void createComment(String memberIdx, Long boardId, CommentDto commentDto) {
        Member member = memberService.getMember(memberIdx);
        Board board = boardRepository.findBoardByBoardIdWithGetComment(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        Comment comment = Comment.createComment(member, board,commentDto.getContent());
        board.addComment(comment);
        commentRepository.save(comment);
    }


}
