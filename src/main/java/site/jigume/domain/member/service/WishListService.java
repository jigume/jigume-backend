package site.jigume.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.dto.wish.WishListDto;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.entity.WishList;
import site.jigume.domain.member.repository.WishListRepository;

import java.util.List;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final WishListRepository wishListRepository;

    @Transactional
    public Long save(Long goodsId) {
        Member member = memberService.getMember();

        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        WishList wishList = WishList.createWishList(member, goods);

        return wishListRepository.save(wishList).getId();
    }

    @Transactional(readOnly = true)
    public List<WishListDto> getWishList() {
        Member member = memberService.getMember();

        List<WishList> wishList = wishListRepository.findWishListByMemberId(member.getId());

        return wishList.stream()
                .map(w -> WishListDto.from(w))
                .toList();
    }

    public void delete(Long goodsId) {
        Member member = memberService.getMember();

        wishListRepository.deleteWishListByGoods_IdAndMemberId(goodsId, member.getId());
    }
}
