package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.GoodsImage;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GoodsImagesDto {

    private String goodsImgUrl;

    private boolean repimgYn;

    public GoodsImagesDto(String goodsImgUrl, boolean repimgYn) {
        this.goodsImgUrl = goodsImgUrl;
        this.repimgYn = repimgYn;
    }

    public static List<GoodsImagesDto> from(List<GoodsImage> goodsImageList) {
        return goodsImageList.stream()
                .map(goodsImage ->
                        new GoodsImagesDto(goodsImage.getGoodsImgUrl(), goodsImage.isRepimgYn()))
                .collect(Collectors.toList());
    }
}
