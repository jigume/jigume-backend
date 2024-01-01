package com.jigume.domain.member.service;

import com.jigume.domain.member.dto.*;
import com.jigume.domain.member.exception.auth.AuthException;
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

import static com.jigume.domain.member.exception.auth.AuthExceptionCode.INVALID_AUTHORIZATION_CODE;

@Service
@RequiredArgsConstructor
public class NaverService implements OAuthService{

    @Value("${NAVER_CLIENT_ID}")
    private String naverClientId;

    @Value("${NAVER_CLIENT_SECRET_KEY}")
    private String naverClientSecretKey;

    @Value("${NAVER_STATE}")
    private String naverState;

    @Value("${NAVER_TOKEN_REQUEST_URI}")
    private String naverTokenRequestUri;

    @Value("${NAVER_USERINFO_REQUEST_URI}")
    private String naverUserinfoRequestUri;

    private final WebClient webClient;

    @Override
    public OAuthTokenResponseDto getOAuthToken(String authorizationCode) {
        NaverTokenRequestDto naverTokenRequestDto = new NaverTokenRequestDto("authorization_code", naverClientId, naverState, authorizationCode, naverClientSecretKey);
        MultiValueMap<String , String> params = naverTokenRequestDto.toMultiValueMap();

        return webClient.post()
                .uri(naverTokenRequestUri)
                .body(BodyInserters.fromFormData(params))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new AuthException(INVALID_AUTHORIZATION_CODE)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();
    }

    @Override
    public OAuthUserDto getOAuthUser(OAuthTokenResponseDto oAuthTokenResponseDto) {
        return webClient.get()
                .uri(naverUserinfoRequestUri)
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
                .header("Authorization","Bearer " + oAuthTokenResponseDto.getAccessToken())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(NaverUserDto.class)
                .block();
    }
}
