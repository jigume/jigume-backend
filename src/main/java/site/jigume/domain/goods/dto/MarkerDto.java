package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Address;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsImage;

@NoArgsConstructor
@Data
public class MarkerDto {
    private Long goodsId;
    private Long categoryId;
    private Address address;
    private String goodsRepImgUrl;

    public static MarkerDto toMarkerDto(Goods goods) {
        MarkerDto markerDto = new MarkerDto();

        markerDto.setGoodsId(goods.getId());
        markerDto.setCategoryId(goods.getCategory().getId());
        markerDto.setAddress(goods.getAddress());

        String goodsRepImgUrl = goods.getGoodsImageList()
                .stream()
                .filter(GoodsImage::isRepimgYn)
                .findAny().get().getGoodsImgUrl();

        markerDto.setGoodsRepImgUrl(goodsRepImgUrl);

        return markerDto;
    }
}
