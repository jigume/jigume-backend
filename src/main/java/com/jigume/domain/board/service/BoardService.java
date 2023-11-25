package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.BoardDto;
import com.jigume.domain.board.dto.UpdateBoardDto;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.exception.exception.BoardNotFoundException;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Board createBoard(String boardContent, Goods goods) {
        Board board = Board.createBoard(boardContent, goods);

        boardRepository.save(board);

        return board;
    }

    public BoardDto getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardNotFoundException());

        Goods goods = board.getGoods();

        Member member = memberService.getMember();
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthNotAuthorizationMemberException();
        }

        return toBoardDto(board);
    }

    @Transactional
    public BoardDto updateBoard(Long boardId, UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardNotFoundException());
        Member member = memberService.getMember();
        isHost(board, member);

        String boardContent = updateBoardDto.getBoardContent();

        board.updateBoardContent(boardContent);

        return toBoardDto(board);
    }

    private void isHost(Board board, Member member) {
        if (board.getGoods().getSell().getMember().equals(member)) {
            throw new AuthNotAuthorizationMemberException();
        }
    }

    private BoardDto toBoardDto(Board board) {
        return new BoardDto().builder().boardName(board.getBoardName())
                .boardContent(board.getBoardContent())
                .hostName(board.getGoods().getSell().getMember().getNickname())
                .created_at(board.getCreatedDate())
                .modified_at(board.getModifiedDate())
                .build();
    }
}
