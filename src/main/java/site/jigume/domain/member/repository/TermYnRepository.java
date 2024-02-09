package site.jigume.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.member.entity.TermYn;

public interface TermYnRepository extends JpaRepository<TermYn, Long> {
}