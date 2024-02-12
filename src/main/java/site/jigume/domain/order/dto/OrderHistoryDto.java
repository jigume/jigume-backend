package site.jigume.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.GoodsImage;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.entity.OrderStatus;

@Data
@NoArgsConstructor
public class OrderHistoryDto {

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
    private Long categoryId;
    private Long boardId;
    private Long orderId;
    private OrderStatus orderStatus;


    public static OrderHistoryDto from(Order order) {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();

        orderHistoryDto.goodsId = order.getGoods().getId();
        orderHistoryDto.goodsLink = order.getGoods().getLink();
        orderHistoryDto.goodsName = order.getGoods().getName();
        orderHistoryDto.sellerInfoDto = SellerInfoDto.from(order.getGoods().getSell().getMember());
        orderHistoryDto.goodsPrice = order.getGoods().getGoodsPrice();
        orderHistoryDto.goodsDeliveryPrice = order.getGoods().getDeliveryFee();
        orderHistoryDto.goodsOrderCount = order.getGoods().getCurrentOrderCount();
        orderHistoryDto.discountDeliveryPrice = order.getGoods().getDeliveryFee() -
                (order.getGoods().getDeliveryFee() / order.getGoods().getCurrentOrderCount());

        //TODO
        orderHistoryDto.repImgUrl = order.getGoods().getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny()
                .get().getFile().getUrl();

        orderHistoryDto.goodsStatus = order.getGoods().getGoodsStatus();
        orderHistoryDto.categoryId = order.getGoods().getCategory().getId();
        orderHistoryDto.boardId = order.getGoods().getBoard().getId();
        orderHistoryDto.orderId = order.getId();
        orderHistoryDto.orderStatus = order.getOrderStatus();

        return orderHistoryDto;
    }
}
