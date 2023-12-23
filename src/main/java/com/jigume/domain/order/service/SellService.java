package com.jigume.domain.order.service;

import com.jigume.domain.goods.dto.GoodsListDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.service.GoodsQueryService;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.SellHistoryDto;
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
    private final GoodsImagesRepository goodsImagesRepository;
    private final GoodsService goodsService;
    private final GoodsQueryService goodsQueryService;

    @Transactional
    public Sell createSell(Member member, Goods goods) {
        Sell sell = Sell.createSell(member, goods);

        sellRepository.save(sell);

        return sell;
    }

    public SellHistoryDto getSellProcessingHistory() {
        Member member = memberService.getMember();
        List<Sell> sellsByMemberId = sellRepository
                .findSellsByMemberIdAndGoodsGoodsStatus(member.getId(), GoodsStatus.PROCESSING);

        List<GoodsListDto> goodsListDtoList = sellsByMemberId.stream()
                .map(sell -> sell.getGoods())
                .map(goods -> GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return new SellHistoryDto(goodsListDtoList);
    }

    //TODO: 판매 완료
//    public SellHistoryDto getSellEndHistory() {
//        Member member = memberService.getMember();
//
//        List<Sell> sellsByMemberId = sellRepository.findSellsByMemberId(member.getId());
//
//        List<Goods> goodsList = sellsByMemberId.stream().filter(sell -> sell.getGoods().getGoodsStatus() == GoodsStatus.END)
//                .map(Sell::getGoods).collect(Collectors.toList());
//
//        List<GoodsListDto> goodsDtoList = goodsQueryService.getGoodsList(goodsList);
//
//        List<SellInfoDto> sellInfoDtoList = toSellInfoDtoList(goodsList);
//
//        SellHistoryDto sellHistoryDto = SellHistoryDto.builder().goodsDtoList(goodsDtoList)
//                .sellInfoDtoList(sellInfoDtoList).build();
//
//        return sellHistoryDto;
//    }
}
