package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_DELETE_IMPOSSIBLE;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsDeleteService {

    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    public void goodsDelete(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdForDelete(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        checkGoodsSeller(goods, member);

        if (goods.getOrderList().size() > 0 || goods.getGoodsStatus() != GoodsStatus.FINISHED) {
            throw new GoodsException(GOODS_DELETE_IMPOSSIBLE);
        }

        goods.updateEnd();

        goods.delete();
        goods.getBoard().delete();
        goods.getBoard().getCommentList()
                .stream().forEach(
                        comment -> comment.delete()
                );
        goods.getSell().delete();
        goods.getGoodsImageList().stream()
                .forEach(
                        goodsImage -> goodsImage.delete()
                );
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }
}
