package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.entity.Address;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.dto.SellerInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GoodsPageDto {

    private Long goodsId;

    private String goodsName;

    private String introduction;

    private String link;

    private Integer goodsPrice;

    private Integer deliveryFee;

    private Address address;

    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Long categoryId;

    private Integer realDeliveryFee;

    private GoodsStatus goodsStatus;

    private SellerInfoDto sellerInfoDto;

    private Integer goodsOrderCount;

    private Integer discountDeliveryPrice;

    private Long boardId;

    private List<GoodsImagesDto> goodsImagesList = new ArrayList<>();

    public static GoodsPageDto toGoodsPageDto(Goods goods) {
        GoodsPageDto goodsPageDto = new GoodsPageDto();

        goodsPageDto.goodsId = goods.getId();
        goodsPageDto.goodsName = goods.getName();
        goodsPageDto.introduction = goods.getIntroduction();
        goodsPageDto.link = goods.getLink();
        goodsPageDto.goodsPrice = goods.getGoodsPrice();
        goodsPageDto.deliveryFee = goods.getDeliveryFee();
        goodsPageDto.address = goods.getAddress();
        goodsPageDto.goodsLimitCount = goodsPageDto.getGoodsLimitCount();
        goodsPageDto.goodsLimitTime = goodsPageDto.getGoodsLimitTime();
        goodsPageDto.categoryId = goods.getCategory().getId();
        goodsPageDto.realDeliveryFee = goods.getRealDeliveryFee();
        goodsPageDto.goodsStatus = goods.getGoodsStatus();
        goodsPageDto.sellerInfoDto = SellerInfoDto.toSellerInfoDto(goods.getSell().getMember());
        goodsPageDto.goodsOrderCount = goods.getCurrentOrderCount();
        goodsPageDto.discountDeliveryPrice = goods.getDeliveryFee() - goods.getRealDeliveryFee();
        goodsPageDto.boardId = goods.getBoard().getId();
        goodsPageDto.goodsImagesList = GoodsImagesDto.toGoodsImagesDto(goods.getGoodsImageList());

        return goodsPageDto;
    }
}
