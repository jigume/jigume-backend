package com.jigume.domain.member.entity;

import com.jigume.domain.member.exception.auth.exception.InvalidAuthorizationCodeException;
import com.jigume.domain.member.service.AppleService;
import com.jigume.domain.member.service.KakaoService;
import com.jigume.domain.member.service.NaverService;
import com.jigume.domain.member.service.OAuthService;
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
