package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.*;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsMapRepository;
import site.jigume.domain.goods.repository.GoodsPageRepository;
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
public class GoodsPageQueryService {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final GoodsMapRepository goodsMapRepository;
    private final GoodsPageRepository goodsPageRepository;

    public GoodsDetailPageDto getGoodsDetailPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        checkEndTime(goods);

        GoodsMemberAuth isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                GoodsPageDto.toGoodsPageDto(goods));
    }

    public GoodsSliceDto getGoodsListByCategoryIdInMap(Long categoryId, CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Slice<Goods> goodsSlice = goodsPageRepository
                .findGoodsByCategoryAndMapRangeOrderByCreatedDate(categoryId, minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    public GoodsSliceDto getGoodsListInMap(CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Slice<Goods> goodsSlice = goodsMapRepository
                .findGoodsByMapRange(minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    public GoodsSliceDto getGoodsListInIds(List<Long> goodsIds, Pageable pageable) {
        Slice<Goods> goodsByIdIn = goodsPageRepository.findGoodsByIdIn(goodsIds, pageable);

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
