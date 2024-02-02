package site.jigume.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import site.jigume.domain.goods.entity.Goods;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSliceDto {

    private List<GoodsListDto> goodsListDtoList;

    private boolean hasNext;

    public static GoodsSliceDto from(Slice<Goods> goodsSlice) {
        List<Goods> goodsList = goodsSlice.getContent();

        List<GoodsListDto> goodsListDtoList = goodsList.stream()
                .map(g -> GoodsListDto.from(g))
                .collect(Collectors.toList());

        boolean hasNext = goodsSlice.hasNext();

        GoodsSliceDto goodsSliceDto = new GoodsSliceDto(goodsListDtoList, hasNext);
        return goodsSliceDto;
    }
}
