package com.jigume.fixture;

import com.jigume.domain.goods.entity.Category;
import com.jigume.domain.goods.entity.Goods;

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

    public static Category getCategory() {
        return new Category("test");
    }
}
