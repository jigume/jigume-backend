package com.jigume.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String nickname;

    private Long mapX;

    private Long mapY;

}
