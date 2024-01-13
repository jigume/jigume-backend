package site.jigume.domain.order.dto;

import lombok.Data;
import site.jigume.domain.member.entity.Member;

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
