package com.jigume.entity.member;

import com.jigume.exception.auth.exception.InvalidAuthorizationCodeException;
import com.jigume.service.member.KakaoService;
import com.jigume.service.member.NaverService;
import com.jigume.service.member.OAuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum LoginProvider {
    NAVER("naver", NaverService.class), KAKAO("kakao", KakaoService.class), APPLE("apple", AppleService.class);

    private final String provider;

    private final Class<? extends OAuthService> serviceClass;

    public static LoginProvider toLoginProvider(String provider) {
       return Arrays.stream(LoginProvider.values()).filter(loginProvider -> loginProvider.provider.equals(provider))
                .findFirst().orElseThrow(() -> new InvalidAuthorizationCodeException());
    }
}
