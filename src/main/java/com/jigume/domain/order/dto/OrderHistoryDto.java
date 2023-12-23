package com.jigume.domain.order.dto;

import com.jigume.domain.goods.dto.GoodsListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private List<GoodsListDto> goodsListDtoList;
}
