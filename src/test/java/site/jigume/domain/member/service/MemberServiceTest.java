package site.jigume.domain.member.service;

import site.jigume.config.JwtFactory;
import site.jigume.domain.member.dto.MemberInfoDto;
import site.jigume.domain.member.dto.TokenDto;
import site.jigume.domain.member.dto.UpdateMemberInfoDto;
import site.jigume.domain.member.entity.BaseRole;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.repository.MemberRepository;
import site.jigume.global.jwt.JwtProperties;
import site.jigume.global.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static site.jigume.fixture.UserFixture.*;
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
        assertThatThrownBy(() -> memberService.reissueToken(refreshToken)).isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("토큰 재발급 - 실패")
    void reissueToken_fail_token_invalid() {
        String refreshToken = "token";
        assertThatThrownBy(() -> memberService.reissueToken(refreshToken)).isInstanceOf(AuthException.class);
    }


    @Test
    void updateInfo() {
        UpdateMemberInfoDto memberInfoDto = new UpdateMemberInfoDto();
        memberInfoDto.setNickname("test");
        memberInfoDto.setMapX(1.0);
        memberInfoDto.setMapY(3.0);

        setUpCustomAuth(guestMember);
        memberService.updateMemberInfo(memberInfoDto);

        assertThat(guestMember.getBaseRole()).isEqualTo(BaseRole.USER);
        assertThat(guestMember.getNickname()).isEqualTo("test");
    }

    @Test
    void updateUserInfo() {
        UpdateMemberInfoDto memberInfoDto = new UpdateMemberInfoDto();
        memberInfoDto.setNickname("test");
        memberInfoDto.setMapX(1.0);
        memberInfoDto.setMapY(3.0);

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
        assertThatThrownBy(() -> memberService.getMember()).isInstanceOf(AuthException.class);
    }

}