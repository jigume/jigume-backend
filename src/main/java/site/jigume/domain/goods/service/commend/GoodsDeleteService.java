package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.board.repository.CommentRepository;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsImagesRepository;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.repository.SellRepository;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_DELETE_IMPOSSIBLE;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsDeleteService {

    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final SellRepository sellRepository;
    private final GoodsImagesRepository goodsImagesRepository;

    public void goodsDelete(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        checkGoodsSeller(goods, member);

        if (goods.getOrderList().size() > 0) {
            throw new GoodsException(GOODS_DELETE_IMPOSSIBLE);
        }

        sellRepository.deleteSell(goodsId);
        goodsImagesRepository.deleteGoodsImage(goodsId);
        boardRepository.deleteBoard(goodsId);
//        commentRepository.deleteComment(goods.getBoard().getId());
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }
}
