package com.jigume.domain.order.entity;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDelete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public static Sell createSell(Member member, Goods goods) {
        Sell sell = new Sell();
        sell.isDelete = false;
        sell.goods = goods;
        sell.member = member;

        return sell;
    }

    public void deleteSell() {
        this.isDelete = true;
    }
}
