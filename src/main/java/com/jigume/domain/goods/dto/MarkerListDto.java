package com.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class MarkerListDto {
    private List<MarkerDto> markerList = new ArrayList<>();

    public MarkerListDto(List<MarkerDto> markerList) {
        this.markerList = markerList;
    }
}
