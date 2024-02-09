package site.jigume.domain.admin.dto;

import site.jigume.domain.admin.entity.Term;

public record TermDto(String detail, boolean requiredYn) {

    public Term toTerm() {
        return Term.createTerm(this.detail, this.requiredYn);
    }
}
