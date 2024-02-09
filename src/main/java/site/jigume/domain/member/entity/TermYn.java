package site.jigume.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.admin.entity.Term;
import site.jigume.global.audit.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "term_yn")
@NoArgsConstructor
@Getter
public class TermYn extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean agreeYn;

    private LocalDateTime agreeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Term term;

    public static TermYn createTermYn(Term term, Member member, boolean agreeYn) {
        TermYn termYn = new TermYn();
        termYn.term = term;
        termYn.member = member;
        termYn.agreeYn = agreeYn;
        termYn.agreeDate = LocalDateTime.now();

        return termYn;
    }
}
