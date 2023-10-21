package com.jigume.entity.goods;

import com.jigume.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class GoodsImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_images_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private String goodsImgUrl;

    private boolean repimgYn; //대표 이미지 여부

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public void setRepimgYn(boolean repimgYn) {
        this.repimgYn = repimgYn;
    }
}
