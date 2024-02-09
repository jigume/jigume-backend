package site.jigume.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.admin.entity.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
}