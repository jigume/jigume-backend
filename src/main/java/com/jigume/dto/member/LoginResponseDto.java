package com.jigume.dto.member;

import com.jigume.entity.member.BaseRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private TokenDto tokenDto;

    private BaseRole baseRole;
}
