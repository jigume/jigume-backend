package com.jigume.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBoardDto {

    private String nickname;

    private Integer sellCount;

    private Integer orderCount;

    private GoodsDto goodsDto;
}
