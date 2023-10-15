package com.jigume.service.member;

import com.jigume.config.jwt.TokenProvider;
import com.jigume.entity.member.Member;
import com.jigume.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.exception.auth.exception.AuthInvalidRefreshToken;
import com.jigume.repository.MemberRepository;
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
