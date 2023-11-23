package com.jigume.domain.goods.service;

import com.jigume.domain.goods.dto.GoodsDetailPageDto;
import com.jigume.domain.goods.dto.GoodsDto;
import com.jigume.domain.goods.dto.GoodsImagesDto;
import com.jigume.domain.goods.dto.GoodsPageDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<GoodsDto> getGoodsList() {
        List<Goods> goodsList = goodsRepository.findAll();

        goodsList.stream()
                .filter(goods -> checkTime(goods))
                .collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public List<GoodsDto> getGoodsList(List<Goods> goodsList) {
        goodsList.stream()
                .filter(goods -> checkTime(goods))
                .collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public List<GoodsDto> getGoodsList(Long categoryId) {
        List<Goods> goodsList = goodsRepository.findAllByCategoryId(categoryId);

        goodsList.stream()
                .filter(goods -> checkTime(goods))
                .collect(Collectors.toList());


        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    private boolean checkTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goodsService.timeEnd(goods);

            return true;
        }

        return false;
    }

    public List<GoodsDto> toGoodsDtoList(List<Goods> goodsList) {
        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (Goods goods : goodsList) {
            Member hostMember = goods.getSell().getMember();
            int hostSellCount = sellRepository.findSellsByMemberId(hostMember.getId()).size();
            GoodsDto goodsDto = new GoodsDto().builder().goodsId(goods.getId()).goodsName(goods.getName())
                    .goodsStatus(goods.getGoodsStatus())
                    .goodsPrice(goods.getGoodsPrice())
                    .goodsOrderCount(goods.getCurrentOrderCount())
                    .goodsDeliveryPrice(goods.getDeliveryFee())
                    .hostNickName(hostMember.getNickname())
                    .hostSellCount(hostSellCount)
                    .repImgUrl(goodsImagesRepository.findGoodsImageByGoodsIdAndRepimgYn(goods.getId(), true).get().getGoodsImgUrl()).build();

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
                .goodsLimitCount(goods.getGoodsLimitCount()).category(goods.getCategory().getId())
                .realDeliveryFee(goods.getRealDeliveryFee()).goodsStatus(goods.getGoodsStatus())
                .hostNickname(hostMember.getNickname()).hostSellCount(hostSellCount)
                .goodsOrderCount(goods.getCurrentOrderGoodsCount())
                .discountDeliveryPrice(goods.getDeliveryFee() - goods.getRealDeliveryFee())
                .goodsImagesList(GoodsImagesDto.toGoodsImagesDto(goods.getGoodsImageList()))
                .boardId(goods.getBoard().getId())
                .build();


        return goodsPageDto;
    }

    private boolean isOrderOrSell(Member member, Goods goods) {
        if (goods.isSell(member) || goods.isOrder(member)) {
            return true;
        }

        return false;
    }
}
