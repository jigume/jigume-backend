package site.jigume.global.aws.s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.global.aws.s3.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

    File findFileByUrl(String url);
}