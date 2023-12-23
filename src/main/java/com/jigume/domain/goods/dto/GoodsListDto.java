package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsImage;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.dto.SellerInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GoodsListDto {

    private Long goodsId;
    private String goodsName;
    private SellerInfoDto sellerInfoDto;
    private Integer goodsPrice;
    private Integer goodsDeliveryPrice;
    private Integer goodsOrderCount;
    private Integer discountDeliveryPrice;
    private String repImgUrl;
    private GoodsStatus goodsStatus;
    private Long categoryId;


    public static GoodsListDto toGoodsListDto(Goods goods) {
        GoodsListDto goodsListDto = new GoodsListDto();

        goodsListDto.goodsId = goods.getId();
        goodsListDto.goodsName = goods.getName();
        goodsListDto.sellerInfoDto = SellerInfoDto.toSellerInfoDto(goods.getSell().getMember());
        goodsListDto.goodsPrice = goods.getGoodsPrice();
        goodsListDto.goodsDeliveryPrice = goods.getDeliveryFee();
        goodsListDto.goodsOrderCount = goods.getCurrentOrderCount();
        goodsListDto.discountDeliveryPrice = goods.getDeliveryFee() - goods.getRealDeliveryFee();

        goodsListDto.repImgUrl = goods.getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny().get().getGoodsImgUrl();

        goodsListDto.goodsStatus = goods.getGoodsStatus();
        goodsListDto.categoryId = goods.getCategory().getId();

        return goodsListDto;
    }
}
