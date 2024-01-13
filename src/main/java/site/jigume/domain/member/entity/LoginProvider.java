package site.jigume.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.AppleService;
import site.jigume.domain.member.service.KakaoService;
import site.jigume.domain.member.service.NaverService;
import site.jigume.domain.member.service.OAuthService;

import java.util.Arrays;

import static site.jigume.domain.member.exception.auth.AuthExceptionCode.INVALID_AUTHORIZATION_CODE;

@RequiredArgsConstructor
@Getter
public enum LoginProvider {
    NAVER("naver", NaverService.class), KAKAO("kakao", KakaoService.class), APPLE("apple", AppleService.class);

    private final String provider;

    private final Class<? extends OAuthService> serviceClass;

    public static LoginProvider toLoginProvider(String provider) {
        return Arrays.stream(LoginProvider.values()).filter(loginProvider -> loginProvider.provider.equals(provider))
                .findFirst().orElseThrow(() -> new AuthException(INVALID_AUTHORIZATION_CODE));
    }
}
