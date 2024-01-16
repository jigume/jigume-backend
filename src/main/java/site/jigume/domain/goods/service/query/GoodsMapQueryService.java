package site.jigume.domain.goods.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.dto.MarkerDto;
import site.jigume.domain.goods.dto.MarkerListDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.repository.GoodsMapRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsMapQueryService {

    private final GoodsMapRepository goodsMapRepository;

    public MarkerListDto getMapMarker(Double minX, Double maxX, Double minY, Double maxY) {
        List<Goods> goodsByMapRange = goodsMapRepository
                .findGoodsByMapRange(minX, maxX, minY, maxY, GoodsStatus.PROCESSING);

        List<MarkerDto> markerList = goodsByMapRange.stream()
                .map(goods -> MarkerDto.toMarkerDto(goods))
                .collect(Collectors.toList());

        return new MarkerListDto(markerList);
    }

}
