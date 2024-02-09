package site.jigume.domain.member.dto.wish;

import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.member.entity.WishList;

public record WishListDto(Long wishId, GoodsListDto goodsListDto) {

    public static WishListDto from(WishList wish) {
        return new WishListDto(
                wish.getId(),
                GoodsListDto.from(wish.getGoods())
        );
    }
}
