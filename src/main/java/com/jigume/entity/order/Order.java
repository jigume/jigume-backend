package com.jigume.entity.order;

import com.jigume.entity.BaseTimeEntity;
import com.jigume.entity.member.Member;
import com.jigume.entity.goods.Goods;
import com.jigume.exception.order.OrderOverCountException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Order createOrder(Integer orderCount, OrderType orderType,
                                    Goods goods, Member member, Integer currentOrderCount) {
        Order order = new Order();
        if(goods.getGoodsLimitCount() - (currentOrderCount + orderCount) < 0) {
            throw new OrderOverCountException();
        }
        order.orderCount = orderCount;
        order.orderPrice = (goods.getGoodsPrice() * orderCount) + (goods.getDeliveryFee() / (currentOrderCount + 1));
        order.orderType = orderType;
        order.goods = goods;
        order.member = member;

        return order;
    }
}
