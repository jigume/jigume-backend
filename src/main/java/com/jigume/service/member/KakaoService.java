package com.jigume.service.member;

import com.jigume.dto.member.KakaoTokenRequestDto;
import com.jigume.dto.member.KakaoTokenResponseDto;
import com.jigume.dto.member.KakaoUserDto;
import com.jigume.exception.auth.exception.InvalidAuthorizationCodeException;
import com.jigume.exception.global.exception.GlobalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${KAKAO_CLIENT_SECRET_KEY}")
    private String kakaoClientSecretKey;

    @Value("${KAKAO_REDIRECT_URI}")
    private String kakaoRedirectUri;

    @Value("${KAKAO_TOKEN_REQUEST_URI}")
    private String kakaoToeknRequestUri;

    @Value("${KAKAO_USERINFO_REQUEST_URI}")
    private String kakaoUserInfoRequestUri;

    private final WebClient webClient;

    /**
     * 카카오로 부터 토큰을 받는 함수
     */
    public KakaoTokenResponseDto getKakaoToken(String kakaoCode) {

        KakaoTokenRequestDto kakaoTokenRequestDto = new KakaoTokenRequestDto("authorization_code", kakaoClientSecretKey, kakaoRedirectUri, kakaoCode);
        MultiValueMap<String , String> params = kakaoTokenRequestDto.toMultiValueMap();

        return webClient.post()
                .uri(kakaoToeknRequestUri)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new InvalidAuthorizationCodeException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

    }


    /**
     * 카카오로 부터 유저 정보를 받는 함수
     */
    public KakaoUserDto getKakaoUser(KakaoTokenResponseDto token) {

        return webClient.get()
                .uri(kakaoUserInfoRequestUri)
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
                .header("Authorization","Bearer " + token.getAccessToken())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new GlobalServerErrorException()))
                .bodyToMono(KakaoUserDto.class)
                .block();

    }
}
