package com.jigume.domain.member.service;

import com.jigume.domain.member.dto.*;
import com.jigume.domain.member.exception.auth.exception.InvalidAuthorizationCodeException;
import com.jigume.global.exception.GlobalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KakaoService implements OAuthService{

    @Value("${KAKAO_CLIENT_SECRET_KEY}")
    private String kakaoClientSecretKey;

    @Value("${KAKAO_REDIRECT_URI}")
    private String kakaoRedirectUri;

    @Value("${KAKAO_TOKEN_REQUEST_URI}")
    private String kakaoTokenRequestUri;

    @Value("${KAKAO_USERINFO_REQUEST_URI}")
    private String kakaoUserInfoRequestUri;

    @Value("${KAKAO_CLIENT_ID}")
    private String kakaoClientId;


    private final WebClient webClient;

    @Override
    public OAuthTokenResponseDto getOAuthToken(String authorizationCode) {
        KakaoTokenRequestDto kakaoTokenRequestDto = new KakaoTokenRequestDto("authorization_code", kakaoClientId, kakaoRedirectUri, authorizationCode, kakaoClientSecretKey);
        MultiValueMap<String , String> params = kakaoTokenRequestDto.toMultiValueMap();

        return webClient.post()
                .uri(kakaoTokenRequestUri)
                .body(BodyInserters.fromFormData(params))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new InvalidAuthorizationCodeException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();
    }

    @Override
    public OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto) {
        return webClient.get()
                .uri(kakaoUserInfoRequestUri)
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
                .header("Authorization","Bearer " + oAuthTokenResponseDto.getAccessToken())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(KakaoUserDto.class)
                .block();
    }
}
