package com.jigume.dto.image;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ImageListDto {

    private List<ImageUploadRequest> imageUploadRequestList;


    public ImageListDto(List<ImageUploadRequest> imageUploadRequestList) {
        this.imageUploadRequestList = imageUploadRequestList;
    }
}
