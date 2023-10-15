package com.jigume.service.member;

import com.jigume.config.jwt.TokenProvider;
import com.jigume.dto.member.KakaoTokenResponseDto;
import com.jigume.dto.member.KakaoUserDto;
import com.jigume.dto.member.TokenDto;
import com.jigume.entity.member.LoginProvider;
import com.jigume.entity.member.Member;
import com.jigume.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final KakaoService kakaoService;

    public TokenDto login(LoginProvider loginProvider, String code) {

        String accessToken = null;
        String refreshToken = null;

        if (loginProvider == LoginProvider.KAKAO) {
            KakaoTokenResponseDto kakaoToken = kakaoService.getKakaoToken(code);
            KakaoUserDto kakaoUser = kakaoService.getKakaoUser(kakaoToken);

            Member member = loginMember(String.valueOf(kakaoUser.getId()));

            accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
            refreshToken = member.getRefreshToken();

        } else if (loginProvider == LoginProvider.NAVER) {
//            naverService.getNaverToken(code);
        } else {
//            appleService.getAppleToken(code);
        }

        return new TokenDto(accessToken, refreshToken);
    }

    public TokenDto reissueToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new AuthExpiredTokenException();
        }
        Member memberByRefreshToken = memberRepository.findMemberByRefreshToken(refreshToken).orElseThrow(() -> new AuthMemberNotFoundException());
        String accessToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofHours(2));
        refreshToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofDays(14));

        return new TokenDto(accessToken, refreshToken);
    }


    public Member getMember(String token) {
        return memberRepository.findMemberBySocialId(tokenProvider.getUserSocialId(token))
                .orElseThrow(() -> new AuthMemberNotFoundException());
    }

    private Member loginMember(String socialId) {
        Member member = memberRepository.findMemberBySocialId(socialId)
                .orElseGet(() -> Member.createMember(socialId));

        member.updateRefreshToken(tokenProvider.generateToken(member, Duration.ofDays(14)));

        memberRepository.save(member);
        return member;
    }

}
