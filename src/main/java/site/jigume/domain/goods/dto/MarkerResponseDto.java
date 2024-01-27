package site.jigume.domain.goods.dto;

import site.jigume.domain.goods.dto.coordinate.MarkerDto;

public record MarkerResponseDto(Long goodsId, Long categoryId, double latitude, double longitude) {
    public static MarkerResponseDto from(MarkerDto markerDto) {
        return new MarkerResponseDto(
                markerDto.getGoodsId(),
                markerDto.getCategoryId(),
                markerDto.getLatitude(),
                markerDto.getLongitude()
        );
    }
}
