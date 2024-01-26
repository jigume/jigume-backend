package site.jigume.domain.goods.dto.coordinate;

public record CoordinateRequestDto(double latitude, double longitude,
                                   double latitudeDelta, double longitudeDelta) {
}
