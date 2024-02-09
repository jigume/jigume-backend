package site.jigume.global.aws.s3.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.global.audit.BaseTimeEntity;

@NoArgsConstructor
@Entity
@Table(name = "files")
@Getter
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String url;

    private String name;

    private String type;

    @Builder
    public File(String url, String name, String type) {
        this.url = url;
        this.name = name;
        this.type = type;
    }
}
