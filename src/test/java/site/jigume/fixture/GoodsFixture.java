package site.jigume.fixture;

import site.jigume.domain.goods.dto.GoodsSaveDto;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;

import java.time.LocalDateTime;

public class GoodsFixture {

    public static Goods getGoods(Category category) {
        return Goods.createGoods("test", "test", "test", 10000, 5000,
                100.1, 100.2, 100, LocalDateTime.now(), category);
    }

    public static Goods getGoods(Category category, Integer goodsLimitCount) {
        return Goods.createGoods("test", "test", "test", 10000, 5000,
                100.1, 100.2, goodsLimitCount, LocalDateTime.now(), category);
    }

    public static GoodsSaveDto getGoodsSaveDto(Long categoryId) {
        GoodsSaveDto goodsSaveDto = new GoodsSaveDto();
        goodsSaveDto.setGoodsName("test");
        goodsSaveDto.setGoodsPrice(1000);
        goodsSaveDto.setGoodsLimitCount(10);
        goodsSaveDto.setGoodsLimitTime(LocalDateTime.MAX);
        goodsSaveDto.setIntroduction("test");
        goodsSaveDto.setLink("test");
        goodsSaveDto.setMapX(100.1);
        goodsSaveDto.setCategoryId(categoryId);
        goodsSaveDto.setMapY(100.1);
        goodsSaveDto.setDeliveryFee(1000);
        goodsSaveDto.setBoardContent("test");

        return goodsSaveDto;
    }
    public static Category getCategory() {
        return new Category("test");
    }
}
