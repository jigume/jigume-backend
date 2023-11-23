package com.jigume.domain.goods.entity;

import com.jigume.domain.board.entity.Board;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.exception.OrderOverCountException;
import com.jigume.global.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jigume.domain.goods.entity.GoodsStatus.END;
import static com.jigume.domain.goods.entity.GoodsStatus.PROCESSING;
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

    @Column(name = "real_delivery_fee")
    private Integer realDeliveryFee;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    @Column(name = "goods_limit_count")
    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Integer currentOrderCount;

    private Integer currentOrderGoodsCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "goods")
    private List<Order> orderList = new ArrayList<>();

    @OneToOne(mappedBy = "goods")
    private Sell sell;

    @OneToOne(mappedBy = "goods")
    private Board board;

    @OneToMany(mappedBy = "goods")
    private List<GoodsImage> goodsImageList = new ArrayList<>();


    public static Goods createGoods(String name, String introduction, String link, Integer goodsPrice,
                                    Integer deliveryFee, Double mapX, Double mapY, Integer goodsLimitCount,
                                    LocalDateTime goodsLimitTime, Category category) {
        Goods goods = new Goods();
        goods.name = name;
        goods.introduction = introduction;
        goods.link = link;
        goods.goodsPrice = goodsPrice;
        goods.deliveryFee = deliveryFee;
        goods.realDeliveryFee = deliveryFee;
        goods.address = new Address(mapX, mapY);
        goods.goodsStatus = PROCESSING;
        goods.goodsLimitCount = goodsLimitCount;
        goods.goodsLimitTime = goodsLimitTime;
        goods.currentOrderCount = 1;
        goods.currentOrderGoodsCount = 0;
        goods.category = category;

        return goods;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setOrder(Order order) {
        this.orderList.add(order);
    }

    public void updateEnd() {
        this.goodsStatus = END;
    }

    public void updateGoodsOrder(Integer orderGoodsCount) {
        this.currentOrderCount += 1;
        this.currentOrderGoodsCount += orderGoodsCount;

        if (this.currentOrderGoodsCount > this.goodsLimitCount) {
            throw new OrderOverCountException();
        }

        if (this.currentOrderGoodsCount == this.goodsLimitCount) {
            this.goodsStatus = END;
        }

        this.realDeliveryFee = this.deliveryFee / this.currentOrderCount;
    }

    public boolean isSell(Member member) {
        if (this.sell.getMember().equals(member)) {
            return true;
        }

        return false;
    }

    public boolean isOrder(Member member) {
        return this.orderList
                .stream()
                .filter(order -> order.getMember().equals(member))
                .findAny().isPresent();
    }
}
