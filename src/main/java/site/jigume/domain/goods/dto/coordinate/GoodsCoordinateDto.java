package site.jigume.domain.goods.dto.coordinate;

import org.locationtech.jts.geom.Point;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.util.GeometryGenerator;

public record GoodsCoordinateDto(double latitude, double longitude) {
    public GoodsCoordinate toGoodsCoordinate(Goods goods) {
        Point point = GeometryGenerator.generatePoint(latitude, longitude);

        return new GoodsCoordinate(point, goods);
    }

    public Point toPoint() {
        return GeometryGenerator.generatePoint(latitude, longitude);
    }

    public static GoodsCoordinateDto from(GoodsCoordinate goodsCoordinate) {
        return new GoodsCoordinateDto(goodsCoordinate.getLatitude(), goodsCoordinate.getLongitude());
    }
}
