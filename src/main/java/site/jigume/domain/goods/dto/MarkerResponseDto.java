package site.jigume.domain.goods.dto;

import site.jigume.domain.goods.dto.coordinate.MarkerDto;
import site.jigume.domain.goods.entity.GoodsCoordinate;

public record MarkerResponseDto(Long goodsId, Long categoryId, double latitude, double longitude) {
    public static MarkerResponseDto from(MarkerDto markerDto) {
        return new MarkerResponseDto(
                markerDto.getGoodsId(),
                markerDto.getCategoryId(),
                markerDto.getLatitude(),
                markerDto.getLongitude()
        );
    }

    public static MarkerResponseDto from(GoodsCoordinate goodsCoordinate) {
        return new MarkerResponseDto(
                goodsCoordinate.getGoods().getId(),
                goodsCoordinate.getGoods().getCategory().getId(),
                goodsCoordinate.getLatitude(),
                goodsCoordinate.getLongitude()
        );
    }
}
