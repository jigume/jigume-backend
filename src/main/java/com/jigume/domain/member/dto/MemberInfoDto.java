package com.jigume.domain.member.dto;

import com.jigume.domain.member.entity.Member;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    @Size(min = 2, max = 10, message = "2글자 이상 10글자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "한글, 영어, 숫자만 입력 가능합니다.")
    private String nickname;

    private String profileImgUrl;

    private Double mapX;

    private Double mapY;
}
