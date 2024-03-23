package site.jigume.domain.member.service;

import site.jigume.domain.member.dto.OAuthTokenResponseDto;
import site.jigume.domain.member.dto.OAuthUserDto;

public interface OAuthService {

    OAuthTokenResponseDto getOAuthToken(String authorizationCode, String domain);

    OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto);

}
