package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoodsUpdateService {

    private final MemberService memberService;
    private final GoodsService goodsService;

    public void updateGoodsIntroduction(Long goodsId, String introduction) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkGoodsSeller(goods, member);

        goods.updateGoodsIntroduction(introduction);
    }

    public void endGoodsSelling(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkGoodsSeller(goods, member);

        goods.updateEnd();
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }
}
