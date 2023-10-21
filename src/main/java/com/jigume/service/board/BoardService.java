package com.jigume.service.board;

import com.jigume.dto.board.BoardDto;
import com.jigume.entity.board.Board;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.member.Member;
import com.jigume.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.BoardRepository;
import com.jigume.repository.CommentRepository;
import com.jigume.service.goods.GoodsService;
import com.jigume.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

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
