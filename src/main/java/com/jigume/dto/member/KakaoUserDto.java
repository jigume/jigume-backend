package com.jigume.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KakaoUserDto implements Serializable, OAuthUserDto {

    private Long id;

    @JsonProperty("connected_at")
    private String connectedAt;

    public String getId() {
        return String.valueOf(this.id);
    }

}