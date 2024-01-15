package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GoodsSaveDto {

    private String goodsName;
    private String introduction;
    private String link;
    private Integer goodsPrice;
    private Integer deliveryFee;
    private Double mapX;
    private Double mapY;
    private Integer goodsLimitCount;
    private LocalDateTime goodsLimitTime;
    private Long categoryId;


    public Goods toGoods(Category category) {
        Goods goods = Goods.createGoods(this.getGoodsName(), this.getIntroduction(),
                this.getLink(), this.getGoodsPrice(),
                this.getDeliveryFee(), this.getMapX(),
                this.getMapY(), this.getGoodsLimitCount(),
                this.getGoodsLimitTime(), category);

        return goods;
    }
}
