package site.jigume.domain.goods.dto.coordinate;

import lombok.Data;

@Data
public record CoordinateRequestDto(double latitude, double longitude,
                                   double latitudeDelta, double longitudeDelta) {
}
