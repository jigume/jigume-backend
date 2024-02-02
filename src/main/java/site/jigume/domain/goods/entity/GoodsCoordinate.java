package site.jigume.domain.goods.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import site.jigume.global.audit.BaseTimeEntity;

@Table(name = "coordinates")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsCoordinate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinate_id")
    private Long id;

    @Column(columnDefinition = "POINT SRID 5181")
    private Point coordinate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public GoodsCoordinate(final Point coordinate, final Goods goods) {
        this.id = null;
        this.coordinate = coordinate;
        this.goods = goods;
    }

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return coordinate.getY();
    }

    public double getLongitude() {
        return coordinate.getX();
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public Goods getGoods() {
        return goods;
    }

    public void updateCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }
}
