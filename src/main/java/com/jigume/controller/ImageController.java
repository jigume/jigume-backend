package com.jigume.controller;

import com.jigume.dto.goods.GoodsImagesDto;
import com.jigume.service.goods.GoodsImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final GoodsImagesService goodsImagesService;


    @GetMapping("/{goodsId}/image")
    public ResponseEntity getRepGoodsImage(@PathVariable Long goodsId) {
        GoodsImagesDto repImages = goodsImagesService.getRepImages(goodsId);

        return ResponseEntity.ok().
                contentType(MediaType.IMAGE_JPEG)
                .body(repImages.getImages());
    }

}
