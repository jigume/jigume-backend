package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.BoardDto;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.board.repository.CommentRepository;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.domain.member.service.MemberService;
import com.jigume.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.global.exception.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final GoodsService goodsService;
    private final MemberService memberService;

    public BoardDto getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardIdWithGetComment(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        Goods goods = board.getGoods();
        Member member = memberService.getMember();
        if(!goodsService.isOrderOrSell(member, goods)) {
            throw new AuthNotAuthorizationMemberException();
        }

        return BoardDto.toBoardDto(board);
    }
}
