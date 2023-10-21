package com.jigume.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailPageDto {

    private boolean isOrderOrSell;

    private GoodsPageDto goodsPageDto;
}
