package com.jigume.global.image.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequest(
        MultipartFile multipartFile
) {
}