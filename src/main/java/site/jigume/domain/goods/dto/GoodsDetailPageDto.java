package site.jigume.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.service.MemberStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailPageDto {

    private MemberStatus memberStatus;

    private GoodsPageDto goodsPageDto;
}
