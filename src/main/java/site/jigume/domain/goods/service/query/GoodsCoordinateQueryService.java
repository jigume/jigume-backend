package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.dto.MarkerDto;
import site.jigume.domain.goods.dto.coordinate.CoordinateRequestDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.repository.GoodsCoordinateRepository;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.goods.util.GeometryGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.jigume.domain.goods.util.GeometryGenerator.generatePolygon;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsCoordinateQueryService {

    private final GoodsCoordinateRepository goodsCoordinateRepository;
    private final GoodsService goodsService;

    public List<MarkerDto> getMapMarker(CoordinateRequestDto coordinateRequestDto) {
        List<MarkerDto> markerListFromCoordinate = goodsCoordinateRepository
                .findMarkerListFromCoordinate(generatePolygon(coordinateRequestDto),
                        GoodsStatus.PROCESSING);

        return markerListFromCoordinate;
    }

    public GoodsSliceDto getGoodsListByCategoryIdInMap(Long categoryId, CoordinateRequestDto coordinateRequestDto, Pageable pageable) {
        Polygon area = GeometryGenerator.generatePolygon(coordinateRequestDto);

        Slice<Goods> goodsSlice = goodsCoordinateRepository
                .findGoodsByCategoryAndMapRangeOrderByCreatedDate(area, GoodsStatus.PROCESSING, categoryId, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    public GoodsSliceDto getGoodsListInMap(CoordinateRequestDto coordinateRequestDto, Pageable pageable) {
        Polygon area = GeometryGenerator.generatePolygon(coordinateRequestDto);

        //좌표 추가 방법 생각하기
        Slice<Goods> goodsSlice = goodsCoordinateRepository.findGoodsByCoordinate(area, GoodsStatus.PROCESSING, pageable);

        GoodsSliceDto goodsSliceDto = getGoodsSliceDto(goodsSlice);

        return goodsSliceDto;
    }

    private GoodsSliceDto getGoodsSliceDto(Slice<Goods> goodsSlice) {
        List<Goods> goodsList = goodsSlice.stream()
                .filter(goods -> checkEndTime(goods))
                .collect(Collectors.toList());

        List<GoodsListDto> goodsListDtoList = toGoodsListDtoList(goodsList);

        boolean hasNext = goodsSlice.hasNext();

        GoodsSliceDto goodsSliceDto = new GoodsSliceDto(goodsListDtoList, hasNext);
        return goodsSliceDto;
    }

    private List<GoodsListDto> toGoodsListDtoList(List<Goods> goodsList) {
        List<GoodsListDto> goodsListDtoList = new ArrayList<>();

        goodsList.stream().map(goods ->
                        GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return goodsListDtoList;
    }

    private boolean checkEndTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goodsService.timeEnd(goods);
            return true;
        }

        return false;
    }
}
