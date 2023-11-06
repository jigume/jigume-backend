package com.jigume.domain.order.service;

import com.jigume.domain.goods.dto.GoodsDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.SellHistoryDto;
import com.jigume.domain.order.dto.SellInfoDto;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.repository.SellRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final MemberService memberService;
    private final GoodsService goodsService;

    public SellHistoryDto getSellProcessingHistory() {
        Member member = memberService.getMember();

        List<Sell> sellsByMemberId = sellRepository.findSellsByMemberId(member.getId());

        List<Goods> goodsList = sellsByMemberId.stream().filter(sell -> sell.getGoods().getGoodsStatus() == GoodsStatus.PROCESSING)
                .map(Sell::getGoods).collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = goodsService.getGoodsList(goodsList);

        List<SellInfoDto> sellInfoDtoList = toSellInfoDtoList(goodsList);

        SellHistoryDto sellHistoryDto = SellHistoryDto.builder().goodsDtoList(goodsDtoList)
                .sellInfoDtoList(sellInfoDtoList).build();

        return sellHistoryDto;
    }

    public SellHistoryDto getSellEndHistory() {
        Member member = memberService.getMember();

        List<Sell> sellsByMemberId = sellRepository.findSellsByMemberId(member.getId());

        List<Goods> goodsList = sellsByMemberId.stream().filter(sell -> sell.getGoods().getGoodsStatus() == GoodsStatus.END)
                .map(Sell::getGoods).collect(Collectors.toList());

        List<GoodsDto> goodsDtoList = goodsService.getGoodsList(goodsList);

        List<SellInfoDto> sellInfoDtoList = toSellInfoDtoList(goodsList);

        SellHistoryDto sellHistoryDto = SellHistoryDto.builder().goodsDtoList(goodsDtoList)
                .sellInfoDtoList(sellInfoDtoList).build();

        return sellHistoryDto;
    }

    private List<SellInfoDto> toSellInfoDtoList(List<Goods> goodsList) {
        List<SellInfoDto> sellInfoDtoList = goodsList.stream().map(goods ->
                SellInfoDto.builder().goodsPrice(goods.getGoodsPrice())
                        .goodsOrderCount(goods.getCurrentOrderGoodsCount())
                        .deliveryFee(goods.getDeliveryFee())
                        .boardId(goods.getBoard().getId())
                        .orderCount(goods.getCurrentOrderCount())
                        .totalFee(goods.getCurrentOrderGoodsCount() * goods.getGoodsPrice() + goods.getDeliveryFee())
                        .build()).collect(Collectors.toList());
        return sellInfoDtoList;
    }
}
