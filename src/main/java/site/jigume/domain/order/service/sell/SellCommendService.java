package site.jigume.domain.order.service.sell;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.repository.SellRepository;

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
