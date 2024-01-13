package site.jigume.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.dto.GoodsListDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellHistoryDto {

    private List<GoodsListDto> goodsListDtoList = new ArrayList<>();

}
