package site.jigume.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberBySocialId(String socialId);

    @Query("select m from Member m join fetch m.likes where m.socialId = :socialId")
    Optional<Member> findMemberBySocialIdFetchLikes(@Param("socialId") String socialId);
    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findMemberByRefreshToken(String refreshToken);
}