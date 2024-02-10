package site.jigume.domain.order.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SellInfoDto {

    private Long sellId;
    private Integer goodsPrice;
    private Integer deliveryFee;
    private Integer goodsOrderCount;
    private Integer orderCount;
    private Integer totalFee;
    private Long boardId;

    @Builder
    public SellInfoDto(Integer goodsPrice, Integer deliveryFee, Integer goodsOrderCount, Integer orderCount, Integer totalFee, Long boardId) {
        this.goodsPrice = goodsPrice;
        this.deliveryFee = deliveryFee;
        this.goodsOrderCount = goodsOrderCount;
        this.orderCount = orderCount;
        this.totalFee = totalFee;
        this.boardId = boardId;
    }
}
