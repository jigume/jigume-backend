package com.jigume.dto.image;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequest(
        MultipartFile multipartFile
) {
}