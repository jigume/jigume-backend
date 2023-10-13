package com.jigume.service;

import com.jigume.dto.member.JoinMemberDto;
import com.jigume.dto.member.LoginMemberDto;
import com.jigume.entity.member.Member;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.exception.member.LoginMemberException;
import com.jigume.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(JoinMemberDto joinMemberDto) {
        Member member = Member.createMember(joinMemberDto.getMemberIdx(), joinMemberDto.getPassword(),
                joinMemberDto.getNickname(), joinMemberDto.getPhoneNumber());

        memberRepository.save(member);
    }

    public JoinMemberDto login(LoginMemberDto loginMemberDto) {
        Member member = memberRepository.findMemberByMemberIdx(loginMemberDto.getMemberIdx())
                .orElseThrow(() -> new LoginMemberException());

        return new JoinMemberDto().builder().memberIdx(member.getMemberIdx()).nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber()).build();
    }

    public Member getMember(String idx) {
        return memberRepository.findMemberByMemberIdx(idx)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
