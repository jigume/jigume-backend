package com.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CoordinateDto {

    private Point maxPoint;
    private Point minPoint;
}
