package com.jigume.entity.member;

import com.jigume.exception.auth.exception.InvalidAuthorizationCodeException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum LoginProvider {
    NAVER("naver"), KAKAO("kakao"), APPLE("apple");

    private final String provider;

    public static LoginProvider toLoginProvider(String provider) {
       return Arrays.stream(LoginProvider.values()).filter(loginProvider -> loginProvider.provider.equals(provider))
                .findFirst().orElseThrow(() -> new InvalidAuthorizationCodeException());
    }
}
