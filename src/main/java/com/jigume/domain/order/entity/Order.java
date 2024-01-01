package com.jigume.domain.order.entity;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.order.exception.order.OrderException;
import com.jigume.global.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.jigume.domain.order.exception.order.OrderExceptionCode.ORDER_OVER_COUNT;

@Entity
@NoArgsConstructor
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_count")
    private Integer orderGoodsCount;

    @Column(name = "order_price")
    private Integer orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Order createBuyOrder(Integer orderGoodsCount,
                                       Goods goods, Member member) {
        Order order = new Order();

        if (goods.getGoodsLimitCount() - (goods.getCurrentOrderGoodsCount() + orderGoodsCount) < 0) {
            throw new OrderException(ORDER_OVER_COUNT);
        }

        order.orderGoodsCount = orderGoodsCount;
        order.orderPrice = (goods.getGoodsPrice() * orderGoodsCount) + (goods.getDeliveryFee() / (goods.getCurrentOrderCount() + 1));
        order.goods = goods;
        order.member = member;

        return order;
    }

    public void updateOrderPrice(Goods goods) {
        this.orderPrice = (goods.getGoodsPrice() * orderGoodsCount) + (goods.getDeliveryFee() / (goods.getCurrentOrderCount() + 1));
    }
}
