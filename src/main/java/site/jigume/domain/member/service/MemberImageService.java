package site.jigume.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.member.entity.Member;
import site.jigume.global.aws.s3.S3FileUploadService;
import site.jigume.global.aws.s3.entity.File;
import site.jigume.global.aws.s3.repository.FileRepository;
import site.jigume.global.image.ImageUrl;

@Service
@RequiredArgsConstructor
public class MemberImageService {

    private final FileRepository fileRepository;
    private final S3FileUploadService s3FileUploadService;

    public Long update(MultipartFile multipartFile, Member member) {
        File file = s3FileUploadService.uploadFile(multipartFile);

        member.updateProfileImage(file);

        return file.getId();
    }

    public Long setDefault(Member member) {
        File defaultImg = fileRepository.findFileByUrl(ImageUrl.defaultImageUrl);

        member.updateProfileImage(defaultImg);

        return defaultImg.getId();
    }
}
