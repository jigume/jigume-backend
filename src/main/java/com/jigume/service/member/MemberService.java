package com.jigume.service.member;

import com.jigume.config.jwt.TokenProvider;
import com.jigume.dto.member.*;
import com.jigume.entity.goods.ImageUploadRequest;
import com.jigume.entity.member.BaseRole;
import com.jigume.entity.member.LoginProvider;
import com.jigume.entity.member.Member;
import com.jigume.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.repository.MemberRepository;
import com.jigume.service.s3.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final Map<String, OAuthService> oAuthServiceMap;
    private final S3FileUploadService s3FileUploadService;

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

    public TokenDto reissueToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new AuthExpiredTokenException();
        }
        Member memberByRefreshToken = memberRepository.findMemberByRefreshToken(refreshToken).orElseThrow(() -> new AuthMemberNotFoundException());
        String accessToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofHours(2));
        refreshToken = tokenProvider.generateToken(memberByRefreshToken, Duration.ofDays(14));

        return new TokenDto(accessToken, refreshToken);
    }

    public void updateMemberInfo(MemberInfoDto memberInfoDto) {
        Member member = getMember();

        member.updateMemberInfo(memberInfoDto.getNickname(), memberInfoDto.getMapX(), memberInfoDto.getMapY());
    }

    public void updateMemberProfileImage(ImageUploadRequest imageUploadRequest) {
        Member member = getMember();

        String imgUrl = s3FileUploadService.uploadFile(imageUploadRequest.multipartFile());

        member.updateMemberProfileImg(imgUrl);
    }


    public Member getMember() {
        String socialId = getAuthenticatedUser().getUsername();

        return memberRepository.findMemberBySocialId(tokenProvider.getUserSocialId(socialId))
                .orElseThrow(() -> new AuthMemberNotFoundException());
    }

    private Member loginMember(String socialId) {
        Member member = memberRepository.findMemberBySocialId(socialId)
                .orElseGet(() -> Member.createMember(socialId));

        member.updateRefreshToken(tokenProvider.generateToken(member, Duration.ofDays(14)));

        memberRepository.save(member);
        return member;
    }

    private UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthMemberNotFoundException();
        }

        return (UserDetails) authentication.getPrincipal();
    }

}
