package com.jigume.service.member;

import com.jigume.config.JwtFactory;
import com.jigume.config.jwt.JwtProperties;
import com.jigume.config.jwt.TokenProvider;
import com.jigume.dto.member.MemberInfoDto;
import com.jigume.dto.member.TokenDto;
import com.jigume.entity.member.BaseRole;
import com.jigume.entity.member.Member;
import com.jigume.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-local.properties")
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    JwtProperties jwtProperties;

    Member member;
    Member guestMember;

    @BeforeEach
    public void setUp() {
        member = memberRepository.save(createMember());
        guestMember = memberRepository.save(createGuestMember());

        String refreshToken = JwtFactory.builder()
                .build()
                .createToken(jwtProperties, member);

        String guestRefreshToken = JwtFactory.builder()
                .build()
                .createToken(jwtProperties, guestMember);

        member.updateRefreshToken(refreshToken);
        guestMember.updateRefreshToken(guestRefreshToken);
        System.out.println(refreshToken);
        System.out.println(guestRefreshToken);
    }


    @Test
    void getMemberInfo() {
        setUpCustomAuth(member);

        MemberInfoDto memberInfo = memberService.getMemberInfo();
        assertThat(memberInfo.getNickname()).isEqualTo("id");
    }

    @Test
    @DisplayName("토큰 재발급")
    void reissueToken() {
        setUpCustomAuth(member);
        String token = member.getRefreshToken();
        TokenDto tokenDto = memberService.reissueToken(token);
        String accessToken = tokenDto.getAccessToken();

        assertThat("id").isEqualTo(tokenProvider.getUserSocialId(accessToken));
    }

    @Test
    @DisplayName("토큰 재발급 - 실패")
    void reissueToken_fail() {
        Member testMember = Member.createMember("error");
        String refreshToken = JwtFactory.builder()
                .build()
                .createToken(jwtProperties, testMember);
        assertThatThrownBy(() -> memberService.reissueToken(refreshToken)).isInstanceOf(AuthMemberNotFoundException.class);
    }

    @Test
    @DisplayName("토큰 재발급 - 실패")
    void reissueToken_fail_token_invalid() {
        String refreshToken = "token";
        assertThatThrownBy(() -> memberService.reissueToken(refreshToken)).isInstanceOf(AuthExpiredTokenException.class);
    }


    @Test
    void updateInfo() {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setNickname("test");
        memberInfoDto.setProfileImgUrl("test");
        memberInfoDto.setMapX(1L);
        memberInfoDto.setMapY(3L);

        setUpCustomAuth(guestMember);
        memberService.updateMemberInfo(memberInfoDto);

        assertThat(guestMember.getBaseRole()).isEqualTo(BaseRole.USER);
        assertThat(guestMember.getNickname()).isEqualTo("test");
    }

    @Test
    void updateUserInfo() {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setNickname("test");
        memberInfoDto.setProfileImgUrl("test");
        memberInfoDto.setMapX(1L);
        memberInfoDto.setMapY(3L);

        setUpCustomAuth(member);
        memberService.updateMemberInfo(memberInfoDto);

        assertThat(member.getBaseRole()).isEqualTo(BaseRole.USER);
        assertThat(member.getNickname()).isEqualTo("test");
    }

    @Test
    void getMember() {
        setUpCustomAuth(member);
        assertThat(memberRepository.findMemberBySocialId("id").get()).isEqualTo(memberService.getMember());
    }

    @Test
    void getMember_fail() {
        SecurityContextHolder.clearContext();
        assertThatThrownBy(() -> memberService.getMember()).isInstanceOf(AuthMemberNotFoundException.class);
    }

}