package site.jigume.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.board.dto.BoardCreateDto;
import site.jigume.domain.board.dto.BoardDto;
import site.jigume.domain.board.dto.BoardUpdateDto;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.board.exception.exception.BoardException;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.MemberService;

import static site.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;
import static site.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    @Transactional
    public Long save(Long goodsId, BoardCreateDto boardCreateDto) {
        Member member = memberService.getMember();

        Goods goods = getGoods(goodsId);

        if (!goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        Board board = boardCreateDto.toBoard(goods);
        boardRepository.save(board);
        goods.setBoard(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(Long goodsId, Long boardId) {
        Goods goods = getGoods(goodsId);
        Board board = goods.getBoard();

        validBoard(boardId, board);

        Member member = memberService.getMember();
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        return BoardDto.from(board, goods);
    }

    @Transactional
    public BoardDto updateBoard(Long goodsId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Goods goods = getGoods(goodsId);
        Board board = goods.getBoard();

        validBoard(boardId, board);

        checkBoardInGoods(goodsId, goods);
        Member member = memberService.getMember();
        isHost(goods, member);

        String content = boardUpdateDto.getBoardContent();

        board.updateBoardContent(content);

        return BoardDto.from(board, goods);
    }

    private void isHost(Goods goods, Member member) {
        if (goods.getSell().getMember().equals(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }
    }

    private void checkBoardInGoods(Long goodsId, Goods goods) {
        if (goodsId.equals(goods.getId())) {
            throw new GoodsException(GOODS_NOT_FOUND);
        }
    }

    private Goods getGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
        return goods;
    }

    private void validBoard(Long boardId, Board board) {
        if(board.getId() != boardId) {
            throw new BoardException(BOARD_NOT_FOUND);
        }
    }
}
