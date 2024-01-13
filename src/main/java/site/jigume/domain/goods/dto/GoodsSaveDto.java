package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GoodsSaveDto {

    private String goodsName;
    private String introduction;
    private String link;
    private Integer goodsPrice;
    private Integer deliveryFee;
    private Double mapX;
    private Double mapY;
    private Integer goodsLimitCount;
    private LocalDateTime goodsLimitTime;
    private Long categoryId;
    private String boardContent;

}
