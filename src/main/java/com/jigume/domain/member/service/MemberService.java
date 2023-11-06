package com.jigume.domain.member.service;

import com.jigume.domain.member.dto.*;
import com.jigume.domain.member.entity.BaseRole;
import com.jigume.domain.member.entity.LoginProvider;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthExpiredTokenException;
import com.jigume.domain.member.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.domain.member.repository.MemberRepository;
import com.jigume.global.aws.s3.S3FileUploadService;
import com.jigume.global.image.dto.ImageUploadRequest;
import com.jigume.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        if(member.getBaseRole() == BaseRole.GUEST) member.updateBaseRole(BaseRole.USER);
    }

    public void updateMemberProfileImage(ImageUploadRequest imageUploadRequest) {
        Member member = getMember();

        String imgUrl = s3FileUploadService.uploadFile(imageUploadRequest.multipartFile());

        member.updateMemberProfileImg(imgUrl);
    }

    public MemberInfoDto getMemberInfo() {
        Member member = getMember();

        return MemberInfoDto.toMemberInfoDto(member);
    }


    public Member getMember() {
        String socialId = getAuthenticatedUser().getUsername();

        return memberRepository.findMemberBySocialId(socialId)
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

    public void saveMemberImage(MultipartFile multipartFile) {
        Member member = getMember();
        String memberProfileImgUrl = s3FileUploadService.uploadFile(multipartFile);

        member.updateMemberProfileImg(memberProfileImgUrl);
    }
}
