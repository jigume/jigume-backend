package site.jigume.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.entity.OrderStatus;

@NoArgsConstructor
@Data
public class OrderInfo {

    private Long orderId;
    private Long memberId;
    private String nickName;
    private OrderStatus orderStatus;
    private Integer orderPrice;
    private Integer deliveryFee;
    private Integer orderDeposit;
    private Integer totalPrice;
    private Integer orderGoodsCount;

    public static OrderInfo toOrderInfo(Order order) {
        OrderInfo orderInfo = new OrderInfo();
        Goods goods = order.getGoods();

        orderInfo.orderId = order.getId();
        orderInfo.memberId = order.getMember().getId();
        orderInfo.nickName = order.getMember().getNickname();
        orderInfo.orderPrice = order.getOrderPrice();
        orderInfo.deliveryFee = (goods.getDeliveryFee() / goods.getCurrentOrderCount());
        orderInfo.orderDeposit = order.getDeposit();
        orderInfo.totalPrice = order.getOrderPrice() + orderInfo.deliveryFee + order.getDeposit();
        orderInfo.orderGoodsCount = order.getOrderGoodsCount();
        orderInfo.orderStatus = order.getOrderStatus();

        return orderInfo;
    }
}
