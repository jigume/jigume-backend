package com.jigume.repository;

import com.jigume.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberBySocialId(String socialId);
    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findMemberByRefreshToken(String refreshToken);
}