package com.jigume.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBoardDto {

    private String hostNickname;

    private Integer hostSellCount;

    private Integer orderCount;

    private GoodsPageDto goodsPageDto;
}
