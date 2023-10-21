package com.jigume.entity.order;

import com.jigume.entity.BaseTimeEntity;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.member.Member;
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
            throw new OrderOverCountException();
        }

        order.orderGoodsCount = orderGoodsCount;
        order.orderPrice = (goods.getGoodsPrice() * orderGoodsCount) + (goods.getDeliveryFee() / (goods.getCurrentOrderCount() + 1));
        order.goods = goods;
        order.member = member;

        return order;
    }
}
