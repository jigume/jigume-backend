package site.jigume.domain.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.global.audit.BaseTimeEntity;

@Entity
@NoArgsConstructor
@Getter
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
