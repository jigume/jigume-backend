package com.jigume.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinMemberDto {

    @NotBlank
    private String memberIdx;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    @Builder
    public JoinMemberDto(String memberIdx, String password, String nickname, String phoneNumber) {
        this.memberIdx = memberIdx;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
