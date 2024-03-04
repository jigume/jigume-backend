package site.jigume.domain.member.service;

import site.jigume.domain.member.dto.OAuthTokenResponseDto;
import site.jigume.domain.member.dto.OAuthUserDto;

public class AppleService implements OAuthService{
    @Override
    public OAuthTokenResponseDto getOAuthToken(String authorizationCode, String domain) {
        return null;
    }

    @Override
    public OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto) {
        return null;
    }
}
