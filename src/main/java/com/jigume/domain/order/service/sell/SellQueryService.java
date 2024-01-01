package com.jigume.domain.order.service.sell;


import com.jigume.domain.goods.dto.GoodsListDto;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.SellHistoryDto;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellQueryService {

    private final SellRepository sellRepository;
    private final MemberService memberService;

    public SellHistoryDto getSellHistory(GoodsStatus goodsStatus) {
        Member member = memberService.getMember();

        List<Sell> sellsByMemberId = sellRepository
                .findSellsByMemberIdAndGoodsGoodsStatus(member.getId(), goodsStatus);

        List<GoodsListDto> goodsListDtoList = sellsByMemberId.stream()
                .map(sell -> sell.getGoods())
                .map(goods -> GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return new SellHistoryDto(goodsListDtoList);
    }
}
