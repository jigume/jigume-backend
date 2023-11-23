package com.jigume.domain.goods.service;

import com.jigume.domain.goods.entity.Category;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsImage;
import com.jigume.domain.goods.exception.CategoryNotFoundException;
import com.jigume.domain.goods.exception.GoodsNotFoundException;
import com.jigume.domain.goods.repository.CategoryRepository;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.global.aws.s3.S3FileUploadService;
import com.jigume.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsImagesRepository goodsImagesRepository;
    private final S3FileUploadService s3FileUploadService;

    @Transactional
    public void updateImage(List<MultipartFile> imageList, Long goodsId, Integer repImg) {
        Goods goods = getGoods(goodsId);

        IntStream.range(0, imageList.size())
                .forEach(i -> {
                    String goodsImgUrl = s3FileUploadService.uploadFile(imageList.get(i));
                    boolean isRepImg = (repImg != null && i == repImg);
                    GoodsImage goodsImage = GoodsImage.createGoodsImage(goods, goodsImgUrl, isRepImg);
                    goodsImagesRepository.save(goodsImage);
                });
    }

    @Transactional
    public void timeEnd(Goods goods) {
        goods.updateEnd();
    }

    public Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new GoodsNotFoundException());
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new CategoryNotFoundException());
    }
}
