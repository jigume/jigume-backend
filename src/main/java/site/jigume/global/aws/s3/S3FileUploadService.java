package site.jigume.global.aws.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.global.aws.s3.exception.exception.S3InvalidImageException;
import site.jigume.global.aws.s3.repository.FileRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    private final FileRepository fileRepository;

    public site.jigume.global.aws.s3.entity.File uploadFile(MultipartFile uploadFile) {

        String origName = uploadFile.getOriginalFilename();

        String ext = origName.substring(origName.lastIndexOf('.'));

        String saveFileName = UUID.randomUUID().toString().replaceAll("-","") + ext;

        File file = new File(System.getProperty("user.dir") + saveFileName);

        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            throw new S3InvalidImageException();
        }

        TransferManager transferManager = new TransferManager(this.amazonS3Client);

        PutObjectRequest request = new PutObjectRequest(bucket, saveFileName, file);

        Upload upload = transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (InterruptedException e) {
            throw new S3InvalidImageException();
        }

        String imageUrl = defaultUrl + saveFileName;

        file.delete();

        site.jigume.global.aws.s3.entity.File imgFile =
                new site.jigume.global.aws.s3.entity.
                        File().builder().url(imageUrl)
                        .name(saveFileName)
                        .type(ext).build();

        site.jigume.global.aws.s3.entity.File save = fileRepository.save(imgFile);

        return save;
    }
}
