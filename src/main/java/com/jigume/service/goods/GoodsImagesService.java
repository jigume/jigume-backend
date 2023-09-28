package com.jigume.service.goods;

import com.jigume.dto.goods.GoodsImagesDto;
import com.jigume.entity.goods.GoodsImages;
import com.jigume.repository.GoodsImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsImagesService {

    private final GoodsImagesRepository goodsImagesRepository;

    public GoodsImagesDto getRepImages(Long goodsId) {
        GoodsImages goodsImage = goodsImagesRepository.findGoodsImagesByGoodsId(goodsId)
                .stream().filter(goodsImages -> goodsImages.isRepimgYn()).findFirst().get();

        return GoodsImagesDto.toDto(goodsImage);
    }


    public List<GoodsImagesDto> getGoodsImageList(Long goodsId) {
        return goodsImagesRepository.findGoodsImagesByGoodsId(goodsId).stream().map(GoodsImagesDto::toDto)
                .collect(Collectors.toList());
    }
}
