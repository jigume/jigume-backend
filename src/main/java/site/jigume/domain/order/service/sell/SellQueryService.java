package site.jigume.domain.order.service.sell;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.dto.SellHistoryDto;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.repository.SellRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellQueryService {

    private final SellRepository sellRepository;
    private final MemberService memberService;

    public List<SellHistoryDto> getSellHistory(GoodsStatus goodsStatus) {
        Member member = memberService.getMember();

        List<Sell> sellsByMemberId = sellRepository
                .findSellsByMemberIdAndGoodsGoodsStatus(member.getId(), goodsStatus);

        return sellsByMemberId.stream()
                .filter(sell -> sell.getGoods().isDelete() == false)
                .map(sell -> SellHistoryDto.from(sell))
                .toList();
    }
}
