package site.jigume.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.member.entity.BaseRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private TokenDto tokenDto;

    private BaseRole baseRole;
}
