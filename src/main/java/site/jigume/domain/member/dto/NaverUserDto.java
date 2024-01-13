package site.jigume.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserDto implements OAuthUserDto{

    private String id;

}
