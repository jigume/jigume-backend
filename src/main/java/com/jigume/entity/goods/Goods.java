package com.jigume.entity.goods;

import com.jigume.entity.BaseTimeEntity;
import com.jigume.entity.order.Order;
import com.jigume.exception.order.OrderOverCountException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Goods extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    @Column(name = "goods_introduction")
    private String introduction;

    @Column(name = "goods_link")
    private String link;

    @Column(name = "goods_price")
    private Integer goodsPrice;

    @Column(name = "delivery_fee")
    private Integer deliveryFee;

    @Embedded
    private Address address;

    @Column(name = "goods_is_end")
    private boolean isEnd;

    @Column(name = "goods_limit_count")
    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private String hostNickname;

    private Integer currentOrderCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "goods")
    private List<Order> orderList = new ArrayList<>();


    @Builder
    public static Goods createGoods(String name, String introduction, String link, Integer goodsPrice,
                                    Integer deliveryFee, Integer mapX, Integer mapY, Integer goodsLimitCount,
                                    LocalDateTime goodsLimitTime, String hostNickname, Category category) {
        Goods goods = new Goods();
        goods.name = name;
        goods.introduction = introduction;
        goods.link = link;
        goods.goodsPrice = goodsPrice;
        goods.deliveryFee = deliveryFee;
        goods.address = new Address(mapX, mapY);
        goods.isEnd = false;
        goods.goodsLimitCount = goodsLimitCount;
        goods.goodsLimitTime = goodsLimitTime;
        goods.hostNickname = hostNickname;
        goods.currentOrderCount = 0;
        goods.category = category;

        return goods;
    }

    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public void updateCurrentOrder(Integer orderCount) {
        this.currentOrderCount += orderCount;

        if(this.currentOrderCount > this.goodsLimitCount) {
            throw new OrderOverCountException();
        }

        if(this.currentOrderCount == this.goodsLimitCount) this.isEnd = true;
    }
}
