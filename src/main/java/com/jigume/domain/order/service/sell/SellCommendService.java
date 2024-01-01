package com.jigume.domain.order.service.sell;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.repository.SellRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SellCommendService {

    private final SellRepository sellRepository;
    private final MemberService memberService;

    public Sell createSell(Member member, Goods goods) {
        Sell sell = Sell.createSell(member, goods);

        sellRepository.save(sell);

        return sell;
    }
}
