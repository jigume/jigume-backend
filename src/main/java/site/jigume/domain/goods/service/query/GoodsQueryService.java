package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.board.exception.exception.BoardException;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.goods.dto.GoodsDetailPageDto;
import site.jigume.domain.goods.dto.GoodsPageDto;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsCoordinateRepository;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.goods.service.constant.GoodsMemberAuth;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;

import static site.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsQueryService {

    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final BoardRepository boardRepository;
    private final GoodsCoordinateRepository goodsCoordinateRepository;

    public GoodsDetailPageDto getGoodsDetailPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        Board board = boardRepository.findBoardByGoodsId(goodsId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

        GoodsCoordinate goodsCoordinate = goodsCoordinateRepository.findGoodsCoordinateByGoodsId(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        GoodsMemberAuth isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                GoodsPageDto.from(goods, goodsCoordinate, board));
    }

    public GoodsSliceDto getGoodsListInIds(List<Long> goodsIds, Pageable pageable) {
        Slice<Goods> goodsByIdIn = goodsRepository.findGoodsByIdIn(goodsIds, pageable);

        return GoodsSliceDto.from(goodsByIdIn);
    }

    private GoodsMemberAuth isOrderOrSell(Member member, Goods goods) {
        if (goods.isSell(member)) {
            return GoodsMemberAuth.SELLER;
        } else if (goods.isOrder(member)) {
            return GoodsMemberAuth.ORDER;
        }
        return GoodsMemberAuth.NONE;
    }
}
