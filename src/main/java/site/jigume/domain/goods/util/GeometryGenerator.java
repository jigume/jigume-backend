package site.jigume.domain.goods.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import site.jigume.domain.goods.dto.coordinate.CoordinateRequestDto;

public class GeometryGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    private static final int SRID = 5181;

    public static Polygon generatePolygon(final CoordinateRequestDto coordinateRequestDto) {
        final double latitude = coordinateRequestDto.latitude();
        final double longitude = coordinateRequestDto.longitude();
        final double latitudeDelta = coordinateRequestDto.latitudeDelta();
        final double longitudeDelta = coordinateRequestDto.longitudeDelta();

        final double minLatitude = latitude - latitudeDelta;
        final double maxLatitude = latitude + latitudeDelta;
        final double minLongitude = longitude - longitudeDelta;
        final double maxLongitude = longitude + longitudeDelta;

        final Coordinate[] vertexes = new Coordinate[]{
                new Coordinate(maxLongitude, minLatitude),
                new Coordinate(maxLongitude, maxLatitude),
                new Coordinate(minLongitude, maxLatitude),
                new Coordinate(minLongitude, minLatitude),
                new Coordinate(maxLongitude, minLatitude)
        };

        final Polygon polygon = GEOMETRY_FACTORY.createPolygon(vertexes);
        polygon.setSRID(SRID);
        return polygon;
    }

    public static Point generatePoint(double latitude, double longitude) {
        final Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }
}
