package site.jigume.global.aws.s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.jigume.global.aws.s3.entity.File;
import site.jigume.global.image.ImageUrl;

public interface FileRepository extends JpaRepository<File, Long> {

    File findFileByUrl(String url);
}