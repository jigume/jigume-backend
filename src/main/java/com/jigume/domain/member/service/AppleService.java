package com.jigume.domain.member.service;

import com.jigume.domain.member.dto.OAuthTokenResponseDto;
import com.jigume.domain.member.dto.OAuthUserDto;

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
