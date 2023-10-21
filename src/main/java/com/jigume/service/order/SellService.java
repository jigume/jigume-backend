package com.jigume.service.order;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.entity.goods.DefaultImgUrl;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.member.Member;
import com.jigume.entity.order.Sell;
import com.jigume.repository.GoodsImagesRepository;
import com.jigume.repository.SellRepository;
import com.jigume.service.goods.GoodsService;
import com.jigume.service.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsImagesRepository goodsImagesRepository;

    public List<GoodsDto> getSellList() {
        Member member = memberService.getMember();

        List<Sell> sellsByMemberId = sellRepository.findSellsByMemberId(member.getId());

        return getGoodsList(sellsByMemberId.stream().map(sell -> sell.getGoods()).collect(Collectors.toList()));
    }

    public List<GoodsDto> getGoodsList(List<Goods> goodsList) {

        List<GoodsDto> goodsDtoList = goodsService.toGoodsDtoList(goodsList);

        return goodsDtoList;
    }
}
