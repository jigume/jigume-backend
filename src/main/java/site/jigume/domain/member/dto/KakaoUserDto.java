package site.jigume.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class KakaoUserDto implements Serializable, OAuthUserDto {

    private Long id;

    @JsonProperty("connected_at")
    private String connectedAt;

    public String getId() {
        return String.valueOf(this.id);
    }

}