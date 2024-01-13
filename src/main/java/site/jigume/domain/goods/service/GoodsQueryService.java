package site.jigume.domain.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.*;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsQueryService {

    private final GoodsService goodsService;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    public GoodsDetailPageDto getGoodsPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkTime(goods);

        MemberStatus isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                GoodsPageDto.toGoodsPageDto(goods));
    }

    public GoodsSliceDto getGoodsListByCategoryId(Long categoryId, CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Slice<Goods> goodsSlice = goodsRepository
                .findGoodsByCategoryAndMapRangeOrderByCreatedDate(categoryId, minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    public GoodsSliceDto getGoodsList(CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Slice<Goods> goodsSlice = goodsRepository
                .findGoodsByMapRange(minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    public GoodsSliceDto getMarkerGoods(List<Long> goodsIds, Pageable pageable) {
        Slice<Goods> goodsByIdIn = goodsRepository.findGoodsByIdIn(goodsIds, pageable);

        List<GoodsListDto> goodsListDtoList = toGoodsListDtoList(goodsByIdIn.getContent());

        boolean hasNext = goodsByIdIn.hasNext();

        GoodsSliceDto goodsSliceDto = new GoodsSliceDto(goodsListDtoList, hasNext);

        return goodsSliceDto;
    }

    public MarkerListDto getMapMarker(Double minX, Double maxX, Double minY, Double maxY) {
        List<Goods> goodsByMapRange = goodsRepository
                .findGoodsByMapRange(minX, maxX, minY, maxY, GoodsStatus.PROCESSING);

        List<MarkerDto> markerList = goodsByMapRange.stream()
                .map(goods -> MarkerDto.toMarkerDto(goods))
                .collect(Collectors.toList());

        return new MarkerListDto(markerList);
    }

    private boolean checkTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goodsService.timeEnd(goods);
            return true;
        }

        return false;
    }

    private GoodsSliceDto getGoodsSliceDto(Slice<Goods> goodsSlice) {
        List<Goods> goodsList = goodsSlice.stream()
                .filter(goods -> checkTime(goods))
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

    private MemberStatus isOrderOrSell(Member member, Goods goods) {
        if (goods.isSell(member)) {
            return MemberStatus.SELLER;
        } else if (goods.isOrder(member)) {
            return MemberStatus.ORDER;
        }
        return MemberStatus.NONE;
    }
}
