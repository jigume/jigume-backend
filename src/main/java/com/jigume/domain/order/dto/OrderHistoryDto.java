package com.jigume.domain.order.dto;

import com.jigume.domain.goods.dto.GoodsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private List<GoodsDto> goodsDtoList;
}
