package site.jigume.domain.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.order.dto.SellerInfoDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GoodsPageDto {

    private Long goodsId;

    private String goodsName;

    private String introduction;

    private String link;

    private Integer goodsPrice;

    private Integer deliveryFee;

    private GoodsCoordinateDto coordinate;

    private Integer goodsLimitCount;

    private LocalDateTime goodsLimitTime;

    private Long categoryId;

    private Integer realDeliveryFee;

    private GoodsStatus goodsStatus;

    private SellerInfoDto sellerInfoDto;

    private Integer goodsOrderCount;

    private Integer discountDeliveryPrice;

    private Long boardId;

    private List<GoodsImagesDto> goodsImagesList = new ArrayList<>();

    public static GoodsPageDto from(Goods goods, GoodsCoordinate goodsCoordinate) {
        GoodsPageDto goodsPageDto = new GoodsPageDto();

        goodsPageDto.goodsId = goods.getId();
        goodsPageDto.goodsName = goods.getName();
        goodsPageDto.introduction = goods.getIntroduction();
        goodsPageDto.link = goods.getLink();
        goodsPageDto.goodsPrice = goods.getGoodsPrice();
        goodsPageDto.deliveryFee = goods.getDeliveryFee();
        goodsPageDto.goodsLimitCount = goods.getGoodsLimitCount();
        goodsPageDto.goodsLimitTime = goods.getGoodsLimitTime();
        goodsPageDto.categoryId = goods.getCategory().getId();
        goodsPageDto.goodsStatus = goods.getGoodsStatus();
        goodsPageDto.sellerInfoDto = SellerInfoDto.toSellerInfoDto(goods.getSell().getMember());
        goodsPageDto.coordinate = GoodsCoordinateDto.from(goodsCoordinate);
        goodsPageDto.goodsOrderCount = goods.getCurrentOrderCount();
        goodsPageDto.discountDeliveryPrice = goods.getDeliveryFee() - (goods.getDeliveryFee()/ goods.getCurrentOrderCount());
        goodsPageDto.boardId = goods.getBoard().getId();
        goodsPageDto.goodsImagesList = GoodsImagesDto.from(goods.getGoodsImageList());

        return goodsPageDto;
    }
}
