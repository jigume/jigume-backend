package com.jigume.domain.goods.service;

import com.jigume.domain.goods.dto.*;
import com.jigume.domain.goods.entity.Address;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsImage;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsQueryService {

    private final GoodsService goodsService;
    private final MemberService memberService;
    private final SellRepository sellRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsImagesRepository goodsImagesRepository;

    public GoodsDetailPageDto getGoodsPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkTime(goods);

        boolean isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                toGoodsPageDto(goods));
    }

    public List<GoodsDto> getGoodsListByCategoryId(Long categoryId, CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Page<Goods> goodsList = goodsRepository
                .findGoodsByCategoryAndMapRangeOrderByCreatedDate(categoryId, minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        goodsList.stream()
                .filter(goods -> checkTime(goods))
                .collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public List<GoodsDto> getGoodsList(CoordinateDto coordinateDto, Pageable pageable) {
        Point maxPoint = coordinateDto.getMaxPoint();
        Point minPoint = coordinateDto.getMinPoint();

        Page<Goods> goodsList = goodsRepository
                .findGoodsByMapRange(minPoint.x(), maxPoint.x(),
                        minPoint.y(), maxPoint.y(), GoodsStatus.PROCESSING, pageable);

        goodsList.stream()
                .filter(goods -> checkTime(goods))
                .collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public List<GoodsDto> getMarkerGoods(List<Long> goodsIds, Pageable pageable) {
        Page<Goods> goodsByIdIn = goodsRepository.findGoodsByIdIn(goodsIds, pageable);

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsByIdIn);

        return goodsDtoList;
    }

    public MarkerListDto getMapMarker(Double minX, Double maxX, Double minY, Double maxY) {
        List<Goods> goodsByMapRange = goodsRepository
                .findGoodsByMapRange(minX, maxX, minY, maxY, GoodsStatus.PROCESSING);

        List<MarkerDto> markerList = goodsByMapRange.stream()
                .map(goods -> toMarkerDto(goods))
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

    public List<GoodsDto> toGoodsDtoList(Page<Goods> goodsList) {
        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (Goods goods : goodsList) {
            Member hostMember = goods.getSell().getMember();
            int hostSellCount = sellRepository.findSellsByMemberId(hostMember.getId()).size();
            GoodsDto goodsDto = new GoodsDto().builder()
                    .goodsId(goods.getId())
                    .goodsName(goods.getName())
                    .goodsStatus(goods.getGoodsStatus())
                    .goodsPrice(goods.getGoodsPrice())
                    .goodsOrderCount(goods.getCurrentOrderCount())
                    .goodsDeliveryPrice(goods.getDeliveryFee())
                    .hostNickName(hostMember.getNickname())
                    .hostSellCount(hostSellCount)
                    .repImgUrl(goodsImagesRepository.findGoodsImageByGoodsIdAndRepimgYn(goods.getId(), true)
                            .get().getGoodsImgUrl())
                    .categoryId(goods.getCategory().getId())
                    .build();

            goodsDtoList.add(goodsDto);
        }

        return goodsDtoList;
    }


    private GoodsPageDto toGoodsPageDto(Goods goods) {
        Member hostMember = goods.getSell().getMember();
        int hostSellCount = sellRepository.findSellsByMemberId(hostMember.getId()).size();

        GoodsPageDto goodsPageDto = new GoodsPageDto().builder()
                .goodsId(goods.getId()).goodsName(goods.getName())
                .introduction(goods.getIntroduction())
                .link(goods.getLink()).goodsPrice(goods.getGoodsPrice())
                .deliveryFee(goods.getDeliveryFee()).mapX(goods.getAddress().getMapX())
                .mapY(goods.getAddress().getMapY()).goodsLimitTime(goods.getGoodsLimitTime())
                .goodsLimitCount(goods.getGoodsLimitCount()).categoryId(goods.getCategory().getId())
                .realDeliveryFee(goods.getRealDeliveryFee()).goodsStatus(goods.getGoodsStatus())
                .hostNickname(hostMember.getNickname()).hostSellCount(hostSellCount)
                .goodsOrderCount(goods.getCurrentOrderGoodsCount())
                .discountDeliveryPrice(goods.getDeliveryFee() - goods.getRealDeliveryFee())
                .goodsImagesList(GoodsImagesDto.toGoodsImagesDto(goods.getGoodsImageList()))
                .boardId(goods.getBoard().getId())
                .build();


        return goodsPageDto;
    }

    private MarkerDto toMarkerDto(Goods goods) {
        Long goodsId = goods.getId();
        Address address = goods.getAddress();

        MarkerDto markerDto = new MarkerDto();

        markerDto.setGoodsId(goodsId);
        markerDto.setCategoryId(goods.getCategory().getId());
        markerDto.setPoint(new Point(address.getMapX(), address.getMapY()));

        String goodsImgUrl = goods.getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny().get().getGoodsImgUrl();

        markerDto.setGoodsImageUrl(goodsImgUrl);

        return markerDto;
    }

    private boolean isOrderOrSell(Member member, Goods goods) {
        if (goods.isSell(member) || goods.isOrder(member)) {
            return true;
        }

        return false;
    }
}
