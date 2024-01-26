package site.jigume.domain.member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.member.entity.Member;
import site.jigume.global.image.ImageUrl;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    @Size(min = 2, max = 10, message = "2글자 이상 10글자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "한글, 영어, 숫자만 입력 가능합니다.")
    private String nickname;

    private String profileImgUrl;

    private Double latitude;

    private Double longitude;

    public static MemberInfoDto from(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setNickname(member.getNickname());

        if (Optional.of(member.getProfileImageUrl()).isEmpty()) {
            memberInfoDto.setProfileImgUrl(member.getProfileImageUrl());
        } else {
            memberInfoDto.setProfileImgUrl(ImageUrl.defaultImageUrl);
        }
        memberInfoDto.setLongitude(member.getCoordinate().getX());
        memberInfoDto.setLatitude(member.getCoordinate().getY());

        return memberInfoDto;
    }
}
