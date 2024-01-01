package com.jigume.domain.order.dto;

import com.jigume.domain.order.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderInfo {

    private Long memberId;
    private String nickName;
    private Integer orderPrice;
    private Integer orderGoodsCount;


    public static OrderInfo toOrderInfo(Order order) {
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.memberId = order.getMember().getId();
        orderInfo.nickName = order.getMember().getNickname();
        orderInfo.orderPrice = order.getOrderPrice();
        orderInfo.orderGoodsCount = order.getOrderGoodsCount();

        return orderInfo;
    }
}
