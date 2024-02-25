package site.jigume.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Goods;

@Entity
@Table(name = "wish_list")
@NoArgsConstructor
@Getter
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public static WishList createWishList(Member member, Goods goods) {
        WishList wishList = new WishList();

        wishList.member = member;
        wishList.goods = goods;

        goods.addLikes(wishList);

        return wishList;
    }
}
