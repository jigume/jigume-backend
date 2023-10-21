package com.jigume.dto.member;

import com.jigume.entity.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String nickname;

    private String profileImgUrl;

    private Double mapX;

    private Double mapY;

    //TODO: 도로명 주소

    public static MemberInfoDto toMemberInfoDto(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setNickname(member.getNickname());
        memberInfoDto.setProfileImgUrl(member.getProfileImageUrl());
        memberInfoDto.setMapX(member.getAddress().getMapX());
        memberInfoDto.setMapY(member.getAddress().getMapY());

        return memberInfoDto;
    }
}
