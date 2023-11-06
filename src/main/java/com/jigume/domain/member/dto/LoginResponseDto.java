package com.jigume.domain.member.dto;

import com.jigume.domain.member.entity.BaseRole;
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
