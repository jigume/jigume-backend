package com.jigume.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor
@AllArgsConstructor
public class KakaoTokenRequestDto {

    private String grantType;
    private String clientId;
    private String redirectUri;
    private String code;
    private String clientSecretKey;

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecretKey);

        return params;
    }
}
