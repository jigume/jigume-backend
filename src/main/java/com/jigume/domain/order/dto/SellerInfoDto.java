package com.jigume.domain.order.dto;

import com.jigume.domain.member.entity.Member;
import lombok.Data;

@Data
public class SellerInfoDto {

    private String sellerNickname;

    private Integer sellCount;

    public static SellerInfoDto toSellerInfoDto(Member hostMember) {
        SellerInfoDto sellerInfoDto = new SellerInfoDto();

        sellerInfoDto.sellerNickname = hostMember.getNickname();
        sellerInfoDto.sellCount = hostMember.getSellList().size();

        return sellerInfoDto;
    }
}
