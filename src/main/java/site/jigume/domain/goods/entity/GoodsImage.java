package site.jigume.domain.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.global.audit.BaseTimeEntity;

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

    public static GoodsImage createGoodsImage(Goods goods, String goodsImgUrl, boolean repimgYn) {
        GoodsImage goodsImage = new GoodsImage();
        goodsImage.goods = goods;
        goodsImage.goodsImgUrl = goodsImgUrl;
        goodsImage.repimgYn = repimgYn;
        goods.getGoodsImageList().add(goodsImage);

        return goodsImage;
    }

}
