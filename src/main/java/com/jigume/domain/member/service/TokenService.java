package com.jigume.domain.member.service;

import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.domain.member.exception.auth.exception.AuthInvalidRefreshToken;
import com.jigume.domain.member.repository.MemberRepository;
import com.jigume.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberRepository memberRepository;
    private TokenProvider tokenProvider;

    public String createNewAccessToken(String refreshToken) {

        if(!tokenProvider.validToken(refreshToken)) {
            throw new AuthExpiredTokenException();
        }

        Member member = memberRepository.findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthInvalidRefreshToken());

        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }

}
