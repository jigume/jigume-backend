package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsCoordinateRepository;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoodsUpdateService {
    private final GoodsCoordinateRepository goodsCoordinateRepository;

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

    public Long updateGoodsCoordinate(Long goodsId, GoodsCoordinateDto goodsCoordinateDto) {
        GoodsCoordinate goodsCoordinate = goodsCoordinateRepository.findGoodsCoordinateByGoodsId(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        goodsCoordinate.updateCoordinate(goodsCoordinateDto.toPoint());

        return goodsCoordinate.getId();
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }
}
