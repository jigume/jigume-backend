package com.jigume.config;

import com.jigume.global.jwt.JwtProperties;
import com.jigume.global.jwt.TokenProvider;
import com.jigume.domain.member.entity.Member;
import com.jigume.fixture.UserFixture;
import com.jigume.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given
        Member testUser = memberRepository.save(UserFixture.createMember());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        String userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("socialId", String.class);

        assertThat(userId).isEqualTo(testUser.getSocialId());
    }

    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        Member testUser = memberRepository.save(UserFixture.createMember());
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties, testUser);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }


    @DisplayName("validToken(): 유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        Member testUser = memberRepository.save(UserFixture.createMember());
        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties, testUser);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }


    @DisplayName("getAuthentication(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        Member testUser = memberRepository.save(UserFixture.createMember());
        // given
        String token = JwtFactory.builder()
                .claims(Map.of("socialId", testUser.getSocialId()))
                .build()
                .createToken(jwtProperties, testUser);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(testUser.getSocialId());
    }

    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        Member testUser = memberRepository.save(UserFixture.createMember());
        String socialId = "id";
        String token = JwtFactory.builder()
                .claims(Map.of("socialId", socialId))
                .build()
                .createToken(jwtProperties, testUser);

        // when
        String userIdByToken = tokenProvider.getUserSocialId(token);

        // then
        assertThat(userIdByToken).isEqualTo(socialId);
    }
}
