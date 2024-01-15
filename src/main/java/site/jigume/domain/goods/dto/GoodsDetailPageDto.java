package site.jigume.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.service.constant.GoodsMemberAuth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailPageDto {

    private GoodsMemberAuth goodsMemberAuth;

    private GoodsPageDto goodsPageDto;
}
