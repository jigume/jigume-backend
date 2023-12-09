package com.jigume.domain.order.dto;

import com.jigume.domain.goods.dto.GoodsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellHistoryDto {

    private List<GoodsDto> goodsDtoList = new ArrayList<>();

}
