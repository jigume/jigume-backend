package site.jigume.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberBySocialId(String socialId);
    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findMemberByRefreshToken(String refreshToken);
}