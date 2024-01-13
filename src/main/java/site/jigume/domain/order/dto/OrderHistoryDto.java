package site.jigume.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.dto.GoodsListDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private List<GoodsListDto> goodsListDtoList;
}
