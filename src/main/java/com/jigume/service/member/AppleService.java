package com.jigume.service.member;

import com.jigume.dto.member.OAuthTokenResponseDto;
import com.jigume.dto.member.OAuthUserDto;

public class AppleService implements OAuthService{
    @Override
    public OAuthTokenResponseDto getOAuthToken(String authorizationCode) {
        return null;
    }

    @Override
    public OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto) {
        return null;
    }
}
