package com.jigume.domain.goods.dto;

import com.jigume.domain.goods.service.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailPageDto {

    private MemberStatus memberStatus;

    private GoodsPageDto goodsPageDto;
}
