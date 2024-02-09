package site.jigume.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.global.audit.BaseTimeEntity;

@Entity
@Table(name = "terms")
@NoArgsConstructor
@Getter
public class Term extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    private String detail;

    private boolean requiredYn;

    public static Term createTerm(String detail, boolean requiredYn) {
        Term term = new Term();
        term.detail = detail;
        term.requiredYn = requiredYn;

        return term;
    }
}
