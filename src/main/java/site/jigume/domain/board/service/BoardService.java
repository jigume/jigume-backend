package site.jigume.domain.board.service;

import site.jigume.domain.board.dto.BoardCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        if(!goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        Board board = boardCreateDto.toBoard(goods);
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(Long goodsId, Long boardId) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

        Goods goods = board.getGoods();

        checkBoardInGoods(goodsId, goods);

        Member member = memberService.getMember();
        if (goods.isOrder(member) || goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        return toBoardDto(board);
    }

    @Transactional
    public BoardDto updateBoard(Long goodsId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        checkBoardInGoods(goodsId, board.getGoods());
        Member member = memberService.getMember();
        isHost(board, member);

        String boardContent = boardUpdateDto.getBoardContent();

        board.updateBoardContent(boardContent);

        return toBoardDto(board);
    }

    private void isHost(Board board, Member member) {
        if (board.getGoods().getSell().getMember().equals(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }
    }

    private BoardDto toBoardDto(Board board) {
        return new BoardDto().builder().boardName(board.getTitle())
                .boardContent(board.getContent())
                .hostName(board.getGoods().getSell().getMember().getNickname())
                .created_at(board.getCreatedDate())
                .modified_at(board.getModifiedDate())
                .build();
    }

    private void checkBoardInGoods(Long goodsId, Goods goods) {
        if (goodsId.equals(goods.getId())) {
            throw new GoodsException(GOODS_NOT_FOUND);
        }
    }
}
