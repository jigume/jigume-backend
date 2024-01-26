package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.entity.GoodsImage;

@Data
public record MarkerDto(Long goodsId, Long categoryId, double latitude, double longitude) {
}
