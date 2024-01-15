package site.jigume.domain.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.exception.order.OrderException;
import site.jigume.global.audit.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static site.jigume.domain.goods.entity.GoodsStatus.END;
import static site.jigume.domain.goods.entity.GoodsStatus.PROCESSING;
import static site.jigume.domain.order.exception.order.OrderExceptionCode.ORDER_NOT_FOUND;

@Entity
@Table(name = "Goods", indexes = {
        @Index(name = "idx_goods", columnList = "")
})
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

    private Integer goodsPrice;

    private Integer deliveryFee;

    private Integer currentOrderCount;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    @Column(name = "goods_limit_count")
    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private boolean isDelete;

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
        goods.address = new Address(mapX, mapY);
        goods.goodsStatus = PROCESSING;
        goods.goodsLimitCount = goodsLimitCount;
        goods.goodsLimitTime = goodsLimitTime;
        goods.isDelete = false;
        goods.category = category;

        return goods;
    }

    public void initGoods(Sell sell, Board board) {
        this.sell = sell;
        this.board = board;
    }

    public void addOrder(Order order) {
        this.orderList.add(order);
    }

    public void updateEnd() {
        this.goodsStatus = END;
    }

    public void updateCurrentOrderCount() {
        this.currentOrderCount += 1;
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

    public void updateGoodsIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
