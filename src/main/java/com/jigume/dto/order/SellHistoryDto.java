package com.jigume.dto.order;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.goods.GoodsPageDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SellHistoryDto {


    private List<GoodsDto> goodsDtoList = new ArrayList<>();

    private List<SellInfoDto> sellInfoDtoList = new ArrayList<>();

    @Builder
    public SellHistoryDto(List<GoodsDto> goodsDtoList, List<SellInfoDto> sellInfoDtoList) {
        this.goodsDtoList = goodsDtoList;
        this.sellInfoDtoList = sellInfoDtoList;
    }
}
