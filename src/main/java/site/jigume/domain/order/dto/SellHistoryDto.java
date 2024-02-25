package site.jigume.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.GoodsImage;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Sell;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellHistoryDto {

    private Long goodsId;
    private String goodsLink;
    private String goodsName;
    private SellerInfoDto sellerInfoDto;
    private Integer goodsPrice;
    private Integer goodsDeliveryPrice;
    private Integer goodsOrderCount;
    private Integer discountDeliveryPrice;
    private String repImgUrl;
    private GoodsStatus goodsStatus;
    private LocalDateTime goodsLimitTime;
    private Long categoryId;
    private Long boardId;
    private Long sellId;


    public static SellHistoryDto from(Sell sell) {
        SellHistoryDto sellHistoryDto = new SellHistoryDto();

        sellHistoryDto.goodsId = sell.getGoods().getId();
        sellHistoryDto.goodsLink = sell.getGoods().getLink();
        sellHistoryDto.goodsName = sell.getGoods().getName();
        sellHistoryDto.sellerInfoDto = SellerInfoDto.from(sell.getMember());
        sellHistoryDto.goodsPrice = sell.getGoods().getGoodsPrice();
        sellHistoryDto.goodsDeliveryPrice = sell.getGoods().getDeliveryFee();
        sellHistoryDto.goodsOrderCount = sell.getGoods().getCurrentOrderCount();
        sellHistoryDto.discountDeliveryPrice = sell.getGoods().getDeliveryFee() -
                (sell.getGoods().getDeliveryFee() / sell.getGoods().getCurrentOrderCount());

        //TODO
        sellHistoryDto.repImgUrl = sell.getGoods().getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny()
                .get().getFile().getUrl();

        sellHistoryDto.goodsStatus = sell.getGoods().getGoodsStatus();
        sellHistoryDto.goodsLimitTime = sell.getGoods().getGoodsLimitTime();
        sellHistoryDto.categoryId = sell.getGoods().getCategory().getId();
        sellHistoryDto.boardId = sell.getGoods().getBoard().getId();
        sellHistoryDto.sellId = sell.getId();

        return sellHistoryDto;
    }

}
