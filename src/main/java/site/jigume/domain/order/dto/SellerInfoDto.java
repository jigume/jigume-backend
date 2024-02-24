package site.jigume.domain.order.dto;

import lombok.Data;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.member.entity.Member;

@Data
public class SellerInfoDto {

    private String sellerNickname;
    private String sellerProfileImage;

    private Integer sellCount;

    public static SellerInfoDto from(Member hostMember) {
        SellerInfoDto sellerInfoDto = new SellerInfoDto();

        sellerInfoDto.sellerNickname = hostMember.getNickname();
        sellerInfoDto.sellerProfileImage = hostMember.getFile().getUrl();
        sellerInfoDto.sellCount = (int) hostMember.getSellList()
                .stream()
                .filter(sell -> sell.getGoods().getGoodsStatus().equals(GoodsStatus.FINISHED))
                .count();

        return sellerInfoDto;
    }
}
