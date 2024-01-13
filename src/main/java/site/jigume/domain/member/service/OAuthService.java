package site.jigume.domain.member.service;

import site.jigume.domain.member.dto.OAuthTokenResponseDto;
import site.jigume.domain.member.dto.OAuthUserDto;

public interface OAuthService {

    abstract OAuthTokenResponseDto getOAuthToken(String authorizationCode);

    abstract OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto);

}
