package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsImage;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.dto.SellerInfoDto;

@NoArgsConstructor
@Data
public class GoodsListDto {

    private Long goodsId;
    private String goodsName;
    private SellerInfoDto sellerInfoDto;
    private Integer goodsPrice;
    private Integer goodsDeliveryPrice;
    private Integer goodsDeposit;
    private Integer goodsOrderCount;
    private Integer discountDeliveryPrice;
    private String repImgUrl;
    private GoodsStatus goodsStatus;
    private Integer likesCount;
    private Long categoryId;


    public static GoodsListDto from(Goods goods) {
        GoodsListDto goodsListDto = new GoodsListDto();

        goodsListDto.goodsId = goods.getId();
        goodsListDto.goodsName = goods.getName();
        goodsListDto.sellerInfoDto = SellerInfoDto.from(goods.getSell().getMember());
        goodsListDto.goodsPrice = goods.getGoodsPrice();
        goodsListDto.goodsDeliveryPrice = goods.getDeliveryFee();
        goodsListDto.goodsDeposit = goods.getDeposit();
        goodsListDto.goodsOrderCount = goods.getCurrentOrderCount();
        goodsListDto.discountDeliveryPrice = goods.getDeliveryFee() -
                (goods.getDeliveryFee() / goods.getCurrentOrderCount());

        //TODO
        goodsListDto.repImgUrl = goods.getGoodsImageList()
                .stream()
                .filter(goodsImage -> goodsImage.isDelete() == false)
                .filter(GoodsImage::isRepimgYn)
                .findAny()
                .get().getFile().getUrl();

        goodsListDto.goodsStatus = goods.getGoodsStatus();
        goodsListDto.likesCount = Math.toIntExact(goods.getLikes().stream().count());
        goodsListDto.categoryId = goods.getCategory().getId();

        return goodsListDto;
    }
}
