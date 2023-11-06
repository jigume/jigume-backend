package com.jigume.domain.member.service;

import com.jigume.domain.member.dto.OAuthTokenResponseDto;
import com.jigume.domain.member.dto.OAuthUserDto;

public interface OAuthService {

    abstract OAuthTokenResponseDto getOAuthToken(String authorizationCode);

    abstract OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto);

}
