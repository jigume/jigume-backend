package site.jigume.global.image;

import org.springframework.beans.factory.annotation.Value;

public class ImageUrl {

    @Value("${cloud.aws.s3.bucket.default.image}")
    public static String defaultImageUrl;
}
