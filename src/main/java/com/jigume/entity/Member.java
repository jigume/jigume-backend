package com.jigume.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "MEMBER")
@NoArgsConstructor
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_idx", unique = true)
    private String memberIdx;

    @Column(name = "member_password")
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(name = "member_phone_number", unique = true)
    private String phoneNumber;


    public static Member createMember(String memberIdx, String password,
                                      String nickname, String phoneNumber) {
        Member member = new Member();

        member.memberIdx = memberIdx;
        member.password = password;
        member.nickname = nickname;
        member.phoneNumber = phoneNumber;

        return member;
    }
}
