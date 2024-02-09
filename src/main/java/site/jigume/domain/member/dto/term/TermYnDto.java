package site.jigume.domain.member.dto.term;

import site.jigume.domain.admin.entity.Term;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.entity.TermYn;

public record TermYnDto(Long termId, boolean termYn) {

    public TermYn toTermYn(Term term, Member member) {
        return TermYn.createTermYn(term, member, this.termYn);
    }
}
