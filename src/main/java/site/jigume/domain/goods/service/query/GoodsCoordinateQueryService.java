package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.dto.coordinate.MarkerResponseDto;
import site.jigume.domain.goods.dto.coordinate.CoordinateRequestDto;
import site.jigume.domain.goods.dto.coordinate.MarkerDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.repository.GoodsCoordinateRepository;
import site.jigume.domain.goods.util.GeometryGenerator;

import java.util.List;
import java.util.stream.Collectors;

import static site.jigume.domain.goods.util.GeometryGenerator.generatePolygon;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsCoordinateQueryService {

    private final GoodsCoordinateRepository goodsCoordinateRepository;

    public List<MarkerResponseDto> getMapMarker(CoordinateRequestDto coordinateRequestDto) {
        List<MarkerDto> markerListFromCoordinate = goodsCoordinateRepository
                .findMarkerListFromCoordinate(generatePolygon(coordinateRequestDto),
                        GoodsStatus.PROCESSING);

        return markerListFromCoordinate.stream()
                .map(m -> MarkerResponseDto.from(m))
                .collect(Collectors.toList());
    }

    public GoodsSliceDto getGoodsListByCategoryIdInMap(Long categoryId, CoordinateRequestDto coordinateRequestDto, Pageable pageable) {
        Polygon area = GeometryGenerator.generatePolygon(coordinateRequestDto);

        Slice<Goods> goodsSlice = goodsCoordinateRepository
                .findGoodsByCoordinateWithCategory(area, GoodsStatus.PROCESSING, categoryId, pageable);

        return GoodsSliceDto.from(goodsSlice);
    }

    public GoodsSliceDto getGoodsListInMap(CoordinateRequestDto coordinateRequestDto, Pageable pageable) {
        Polygon area = GeometryGenerator.generatePolygon(coordinateRequestDto);

        //좌표 추가 방법 생각하기
        Slice<Goods> goodsSlice = goodsCoordinateRepository.findGoodsByCoordinate(area, GoodsStatus.PROCESSING, pageable);

        return GoodsSliceDto.from(goodsSlice);
    }
}
