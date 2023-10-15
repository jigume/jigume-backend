package com.jigume.entity.goods;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequest(
        MultipartFile multipartFile
) {
}