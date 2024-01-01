package com.jigume.domain.goods.entity;

import com.jigume.domain.goods.repository.CategoryRepository;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.repository.MemberRepository;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.repository.SellRepository;
import com.jigume.fixture.GoodsFixture;
import com.jigume.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-local.properties")
class GoodsTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    SellRepository sellRepository;
    @Autowired
    CategoryRepository categoryRepository;
    Member test;

    @BeforeEach
    public void setUp() {
        test = UserFixture.createCustomMember("test");

        memberRepository.save(test);
        UserFixture.setUpCustomAuth(test);
    }

    @DisplayName("판매자 인지 체크한다.")
    @Test
    public void isSell() throws Exception {
        //given
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save);
        goodsRepository.save(goods);
        Sell sell = Sell.createSell(test, goods);
        goods.setSell(sell);

        //when
        boolean sellB = goods.isSell(test);

        //then
        assertThat(sellB).isTrue();
    }

    @DisplayName("판매자 인지 체크한다.")
    @Test
    public void isSell_false() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save);
        goodsRepository.save(goods);
        Sell sell = Sell.createSell(test, goods);
        goods.setSell(sell);

        //when
        boolean sellB = goods.isSell(member);

        //then
        assertThat(sellB).isFalse();
    }

    @DisplayName("구매자 인지 체크한다.")
    @Test
    public void isOrder() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save);
        goodsRepository.save(goods);

        //when
        Order order = Order.createBuyOrder(1, goods, member);
        goods.setOrder(order);
        boolean isOrder = goods.isOrder(member);

        //then
        assertThat(isOrder).isTrue();
    }

    @DisplayName("구매자 인지 체크한다.")
    @Test
    public void isOrder_Fail() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save);
        goodsRepository.save(goods);

        //when
        Order order = Order.createBuyOrder(1, goods, member);
        goods.setOrder(order);
        boolean isOrder = goods.isOrder(test);

        //then
        assertThat(isOrder).isFalse();
    }

    @DisplayName("상품 상태를 종료로 바꾼다")
    @Test
    public void updateEnd() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save);
        goodsRepository.save(goods);

        //when
        goods.updateEnd();

        //then
        assertThat(goods.getGoodsStatus()).isEqualTo(GoodsStatus.END);
    }

    @DisplayName("주문 시 상품 종료 조건 및 주문 조건에 충족하는지 판단한다.")
    @Test
    public void updateGoodsOrder() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save, 2);
        goodsRepository.save(goods);

        //when
        goods.updateGoodsOrder(2);

        //then
        assertThat(goods.getGoodsStatus()).isEqualTo(GoodsStatus.END);
    }

    @DisplayName("주문 시 상품 종료 조건 및 주문 조건에 충족하는지 판단한다.")
    @Test
    public void updateGoodsOrder_1() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save, 2);
        goodsRepository.save(goods);

        //when
        goods.updateGoodsOrder(1);
        goods.updateGoodsOrder(1);

        //then
        assertThat(goods.getGoodsStatus()).isEqualTo(GoodsStatus.END);
    }

    @DisplayName("주문 시 상품 종료 조건 및 주문 조건에 충족하는지 판단한다.")
    @Test
    public void updateGoodsOrder_Fail() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save, 2);
        goodsRepository.save(goods);

        //when
        assertThatThrownBy(() -> goods.updateGoodsOrder(3))
                .isInstanceOf(OrderOverCountException.class);
    }

    @DisplayName("주문 시 상품 종료 조건 및 주문 조건에 충족하는지 판단한다.")
    @Test
    public void updateGoodsOrder_Fail_2() throws Exception {
        //given
        Member member = UserFixture.createMember();
        Category save = categoryRepository.save(GoodsFixture.getCategory());
        Goods goods = GoodsFixture.getGoods(save, 2);
        goodsRepository.save(goods);

        goods.updateGoodsOrder(1);


        //when
        assertThatThrownBy(() -> goods.updateGoodsOrder(2))
                .isInstanceOf(OrderOverCountException.class);
    }
}