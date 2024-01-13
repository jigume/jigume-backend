package site.jigume.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverTokenRequestDto {

    private String grantType;

    private String clientId;

    private String state;

    private String code;

    private String clientSecretKey;

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("state", state);
        params.add("code", code);
        params.add("client_secret", clientSecretKey);

        return params;
    }
}
