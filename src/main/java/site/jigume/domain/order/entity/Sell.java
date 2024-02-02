package site.jigume.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.member.entity.Member;
import site.jigume.global.audit.BaseTimeEntity;

@Entity
@Table(name = "sells")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sell extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isDelete;

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
