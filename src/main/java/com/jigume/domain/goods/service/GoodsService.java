package com.jigume.domain.goods.service;

import com.jigume.domain.goods.entity.Category;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsImage;
import com.jigume.domain.goods.exception.GoodsException;
import com.jigume.domain.goods.repository.CategoryRepository;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.global.aws.s3.S3FileUploadService;
import com.jigume.global.image.ImageUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

import static com.jigume.domain.goods.exception.GoodsExceptionCode.CATEGORY_NOT_FOUND;
import static com.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

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
        if (imageList.size() != 0) {
            IntStream.range(0, imageList.size())
                    .forEach(i -> {
                        String goodsImgUrl = s3FileUploadService.uploadFile(imageList.get(i));
                        boolean isRepImg = (repImg != null && i == repImg);
                        log.info("{}", isRepImg);
                        GoodsImage goodsImage = GoodsImage.createGoodsImage(goods, goodsImgUrl, isRepImg);
                        goodsImagesRepository.save(goodsImage);
                    });
            return;
        }

        GoodsImage goodsImage = GoodsImage.createGoodsImage(goods, ImageUrl.defaultImageUrl, true);
        goodsImagesRepository.save(goodsImage);
    }

    @Transactional
    public void timeEnd(Goods goods) {
        goods.updateEnd();
    }

    public Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() ->
                new GoodsException(GOODS_NOT_FOUND));
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(() ->
                new GoodsException(CATEGORY_NOT_FOUND));
    }
}
