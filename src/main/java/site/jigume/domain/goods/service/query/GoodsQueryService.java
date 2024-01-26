package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.GoodsDetailPageDto;
import site.jigume.domain.goods.dto.GoodsListDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsQueryService {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final GoodsCoordinateRepository goodsCoordinateRepository;

    public GoodsDetailPageDto getGoodsDetailPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        checkEndTime(goods);

        GoodsCoordinate goodsCoordinate = goodsCoordinateRepository.findGoodsCoordinateByGoodsId(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        GoodsMemberAuth isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                GoodsPageDto.toGoodsPageDto(goods, goodsCoordinate));
    }

    public GoodsSliceDto getGoodsListInIds(List<Long> goodsIds, Pageable pageable) {
        Slice<Goods> goodsByIdIn = goodsRepository.findGoodsByIdIn(goodsIds, pageable);

        List<GoodsListDto> goodsListDtoList = toGoodsListDtoList(goodsByIdIn.getContent());

        boolean hasNext = goodsByIdIn.hasNext();

        GoodsSliceDto goodsSliceDto = new GoodsSliceDto(goodsListDtoList, hasNext);

        return goodsSliceDto;
    }

    private boolean checkEndTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goodsService.timeEnd(goods);
            return true;
        }

        return false;
    }

    private GoodsMemberAuth isOrderOrSell(Member member, Goods goods) {
        if (goods.isSell(member)) {
            return GoodsMemberAuth.SELLER;
        } else if (goods.isOrder(member)) {
            return GoodsMemberAuth.ORDER;
        }
        return GoodsMemberAuth.NONE;
    }

    private GoodsSliceDto getGoodsSliceDto(Slice<Goods> goodsSlice) {
        List<Goods> goodsList = goodsSlice.stream()
                .filter(goods -> checkEndTime(goods))
                .collect(Collectors.toList());

        List<GoodsListDto> goodsListDtoList = toGoodsListDtoList(goodsList);

        boolean hasNext = goodsSlice.hasNext();

        GoodsSliceDto goodsSliceDto = new GoodsSliceDto(goodsListDtoList, hasNext);
        return goodsSliceDto;
    }

    private List<GoodsListDto> toGoodsListDtoList(List<Goods> goodsList) {
        List<GoodsListDto> goodsListDtoList = new ArrayList<>();

        goodsList.stream().map(goods ->
                        GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return goodsListDtoList;
    }
}
