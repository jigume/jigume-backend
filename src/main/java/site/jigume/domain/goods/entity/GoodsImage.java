package site.jigume.domain.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.global.audit.BaseTimeEntity;
import site.jigume.global.aws.s3.entity.File;

@Entity
@Table(name = "goods_images")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    private boolean repimgYn; //대표 이미지 여부

    private boolean isDelete;

    public static GoodsImage createGoodsImage(Goods goods, File file, boolean repimgYn) {
        GoodsImage goodsImage = new GoodsImage();
        goodsImage.goods = goods;
        goodsImage.file = file;
        goodsImage.repimgYn = repimgYn;
        goodsImage.isDelete = false;
        goods.getGoodsImageList().add(goodsImage);

        return goodsImage;
    }

    public void delete() {
        this.isDelete = true;
    }

}
