package com.jigume.service.member;

import com.jigume.dto.member.OAuthTokenResponseDto;
import com.jigume.dto.member.OAuthUserDto;

public interface OAuthService {

    abstract OAuthTokenResponseDto getOAuthToken(String authorizationCode);

    abstract OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto);

}
