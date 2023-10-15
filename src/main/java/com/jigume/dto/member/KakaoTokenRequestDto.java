package com.jigume.dto.member;

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

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        return params;
    }
}