package site.jigume.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.goods.util.GeometryGenerator;
import site.jigume.domain.member.dto.*;
import site.jigume.domain.member.entity.BaseRole;
import site.jigume.domain.member.entity.LoginProvider;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.member.MemberException;
import site.jigume.domain.member.repository.MemberRepository;
import site.jigume.global.jwt.TokenProvider;

import java.time.Duration;
import java.util.Map;

import static site.jigume.domain.member.exception.auth.AuthExceptionCode.*;
import static site.jigume.domain.member.exception.member.MemberExceptionCode.MEMBER_DUPLICATE_ERROR;
import static site.jigume.domain.member.exception.member.MemberExceptionCode.MEMBER_INVALID_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final Map<String, OAuthService> oAuthServiceMap;
    private final MemberImageService memberImageService;
    private String regex = "^[가-힣a-zA-Z0-9]*$";

    @Transactional
    public LoginResponseDto login(LoginProvider loginProvider, String code) {
        OAuthService oAuthService =
                oAuthServiceMap.get(StringUtils.uncapitalize(loginProvider.getServiceClass().getSimpleName()));

        OAuthTokenResponseDto oAuthToken = oAuthService.getOAuthToken(code);
        OAuthUserDto oAuthUser = oAuthService.getOAuthUser(oAuthToken);

        Member member = loginMember(oAuthUser.getId());

        BaseRole baseRole = member.getBaseRole();

        String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
        String refreshToken = member.getRefreshToken();


        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

        return new LoginResponseDto(tokenDto, baseRole);
    }

    @Transactional
    public TokenDto reissueToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new AuthException(EXPIRED_TOKEN);
        }
        Member memberByRefreshToken = memberRepository.findMemberByRefreshToken(refreshToken).orElseThrow(() -> new AuthException(NOT_AUTHORIZATION_USER));
        String accessToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofHours(2));
        refreshToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofDays(14));

        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public void updateMemberInfo(UpdateMemberInfoDto updateMemberInfoDto) {
        Member member = getMember();

        Point coordinate = GeometryGenerator.generatePoint(updateMemberInfoDto.getLatitude(), updateMemberInfoDto.getLongitude());
        member.updateMemberInfo(updateMemberInfoDto.getNickname(), coordinate);
        if (member.getBaseRole() == BaseRole.GUEST) member.updateBaseRole(BaseRole.USER);
    }

    @Transactional(readOnly = true)
    public MemberInfoDto getMemberInfo() {
        Member member = getMember();

        return MemberInfoDto.from(member);
    }


    public Member getMember() {
        String socialId = getAuthenticatedUser().getUsername();

        return memberRepository.findMemberBySocialId(socialId)
                .orElseThrow(() -> new AuthException(AUTH_MEMBER_NOT_FOUND));
    }

    private Member loginMember(String socialId) {
        Member member = memberRepository.findMemberBySocialId(socialId)
                .orElseGet(() -> save(socialId));

        member.updateRefreshToken(tokenProvider.generateToken(member, Duration.ofDays(14)));

        memberRepository.save(member);

        return member;
    }

    private Member save(String socialId) {
        Member member = Member.createMember(socialId);

        memberImageService.setDefault(member);

        return member;
    }

    private UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthException(AUTH_MEMBER_NOT_FOUND);
        }

        return (UserDetails) authentication.getPrincipal();
    }

    @Transactional
    public Long saveMemberImage(MultipartFile multipartFile) {
        Member member = getMember();
        Long fileId = memberImageService.update(multipartFile, member);

        if(member.getBaseRole() == BaseRole.GUEST) member.updateBaseRole(BaseRole.USER);
        return fileId;
    }

    public void checkDuplicateNickname(String nickname) {
        if (nickname.length() < 2 && nickname.length() > 10) {
            throw new MemberException(MEMBER_INVALID_NICKNAME);
        }

        if (!nickname.matches(regex)) {
            throw new MemberException(MEMBER_INVALID_NICKNAME);
        }

        if (memberRepository.findMemberByNickname(nickname).isPresent()) {
            throw new MemberException(MEMBER_DUPLICATE_ERROR);
        }
    }
}
