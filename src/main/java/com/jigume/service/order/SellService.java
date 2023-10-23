package com.jigume.service.order;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.order.SellHistoryDto;
import com.jigume.dto.order.SellInfoDto;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.goods.GoodsStatus;
import com.jigume.entity.member.Member;
import com.jigume.entity.order.Sell;
import com.jigume.repository.SellRepository;
import com.jigume.service.goods.GoodsService;
import com.jigume.service.member.MemberService;
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
