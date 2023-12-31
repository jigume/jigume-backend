package com.jigume.domain.board.service;

import com.jigume.domain.board.dto.BoardDto;
import com.jigume.domain.board.dto.UpdateBoardDto;
import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.exception.exception.BoardException;
import com.jigume.domain.board.repository.BoardRepository;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.AuthException;
import com.jigume.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;
import static com.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

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

    @Transactional(readOnly = true)
    public BoardDto getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

        Goods goods = board.getGoods();

        Member member = memberService.getMember();
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        return toBoardDto(board);
    }

    @Transactional
    public BoardDto updateBoard(Long boardId, UpdateBoardDto updateBoardDto) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        Member member = memberService.getMember();
        isHost(board, member);

        String boardContent = updateBoardDto.getBoardContent();

        board.updateBoardContent(boardContent);

        return toBoardDto(board);
    }

    private void isHost(Board board, Member member) {
        if (board.getGoods().getSell().getMember().equals(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
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
