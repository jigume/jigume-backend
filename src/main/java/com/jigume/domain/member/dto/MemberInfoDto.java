package com.jigume.domain.member.dto;

import com.jigume.domain.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String nickname;

    private String profileImgUrl;

    private Double mapX;

    private Double mapY;
}
