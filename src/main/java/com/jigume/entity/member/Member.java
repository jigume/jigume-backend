package com.jigume.entity.member;

import com.jigume.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "MEMBER")
@NoArgsConstructor
@Getter
public class Member implements UserDetails extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(name = "member_idx", unique = true)
    private String memberIdx;

    @Column(unique = true)
    private String nickname;

    private String refreshToken;

    @Column(name = "member_phone_number", unique = true)
    private String phoneNumber;


    public static Member createMember(String memberIdx, String password,
                                      String nickname, String phoneNumber) {
        Member member = new Member();

        member.memberIdx = memberIdx;
        member.nickname = nickname;
        member.phoneNumber = phoneNumber;

        return member;
    }
}
