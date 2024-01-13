package site.jigume.domain.goods.service;

import site.jigume.domain.board.entity.Board;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.goods.dto.GoodsSaveDto;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.repository.CategoryRepository;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.repository.MemberRepository;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.repository.SellRepository;
import site.jigume.fixture.GoodsFixture;
import site.jigume.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-local.properties")
class GoodsCommendServiceTest {

    @Autowired
    GoodsCommendService goodsCommendService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SellRepository sellRepository;
    Member test;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        test = UserFixture.createCustomMember("test");

        memberRepository.save(test);
        UserFixture.setUpCustomAuth(test);
    }

    @DisplayName("굿즈를 저장한다.")
    @Test
    void saveGoods() {
        Category category = GoodsFixture.getCategory();
        categoryRepository.save(category);
        GoodsSaveDto goodsSaveDto = GoodsFixture.getGoodsSaveDto(category.getId());

        List<MultipartFile> image = new ArrayList<>();

        Long l = goodsCommendService.saveGoods(goodsSaveDto, image, 1);
        Optional<Sell> sellByGoodsId = sellRepository.findSellByGoodsId(l);
        Board boardByGoodsId = boardRepository.findBoardByGoodsId(l).get();


        Optional<Goods> goodsById = goodsRepository.findGoodsById(l);

        assertThat(goodsById.get().getName()).isEqualTo("test");
        assertThat(goodsById.get().getSell()).isEqualTo(sellByGoodsId.get());
        assertThat(goodsById.get().getBoard()).isEqualTo(boardByGoodsId);
    }

    @Test
    void endGoodsSelling() {
        UserFixture.setUpCustomAuth(test);
        Category category = GoodsFixture.getCategory();
        categoryRepository.save(category);
        GoodsSaveDto goodsSaveDto = GoodsFixture.getGoodsSaveDto(category.getId());

        List<MultipartFile> image = new ArrayList<>();

        Long l = goodsCommendService.saveGoods(goodsSaveDto, image, 1);

        goodsCommendService.endGoodsSelling(l);
        Optional<Goods> goodsById = goodsRepository.findGoodsById(l);

        assertThat(goodsById.get().getGoodsStatus()).isEqualTo(GoodsStatus.END);
    }

    @Test
    void endGoodsSelling_Fail() {
        UserFixture.setUpCustomAuth(test);
        Category category = GoodsFixture.getCategory();
        categoryRepository.save(category);
        GoodsSaveDto goodsSaveDto = GoodsFixture.getGoodsSaveDto(category.getId());

        List<MultipartFile> image = new ArrayList<>();

        Long l = goodsCommendService.saveGoods(goodsSaveDto, image, 1);

        Member member = UserFixture.createMember();
        memberRepository.save(member);
        UserFixture.setUpCustomAuth(member);

        assertThatThrownBy(() -> goodsCommendService.endGoodsSelling(l))
                .isInstanceOf(AuthException.class);
    }
}