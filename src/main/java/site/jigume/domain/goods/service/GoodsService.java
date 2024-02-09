package site.jigume.domain.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsImage;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.CategoryRepository;
import site.jigume.domain.goods.repository.GoodsImagesRepository;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.global.aws.s3.S3FileUploadService;
import site.jigume.global.aws.s3.entity.File;
import site.jigume.global.aws.s3.repository.FileRepository;
import site.jigume.global.image.ImageUrl;

import java.util.List;
import java.util.stream.IntStream;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.CATEGORY_NOT_FOUND;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsImagesRepository goodsImagesRepository;
    private final S3FileUploadService s3FileUploadService;
    private final FileRepository fileRepository;

    @Transactional
    public void updateImage(List<MultipartFile> imageList, Long goodsId, Integer repImg) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        if (imageList.size() != 0) {
            IntStream.range(0, imageList.size())
                    .forEach(i -> {
                        File file = s3FileUploadService.uploadFile(imageList.get(i));
                        boolean isRepImg = (repImg != null && i == repImg);
                        log.info("{}", isRepImg);
                        GoodsImage goodsImage = GoodsImage.createGoodsImage(goods, file, isRepImg);
                        goodsImagesRepository.save(goodsImage);
                    });
        }
    }

    @Transactional
    public Long saveDefaultImage(Goods goods) {
        File fileByUrl = fileRepository.findFileByUrl(ImageUrl.defaultImageUrl);
        GoodsImage goodsImage = GoodsImage.createGoodsImage(goods, fileByUrl, true);

        goodsImagesRepository.save(goodsImage);

        return goodsImage.getId();
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(() ->
                new GoodsException(CATEGORY_NOT_FOUND));
    }
}
