package com.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MarkerDto {
    private Long goodsId;
    private Point point;
}
