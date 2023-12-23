package com.jigume.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSliceDto {

    private List<GoodsListDto> goodsListDtoList;

    private boolean hasNext;
}
