package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.goods.dto.GoodsSaveDto;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsCoordinate;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsCoordinateRepository;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.repository.SellRepository;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoodsCreateService {
    private final GoodsCoordinateRepository goodsCoordinateRepository;

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final SellRepository sellRepository;

    public Long saveGoods(GoodsSaveDto goodsSaveDto,
                          List<MultipartFile> imageList, Integer repImg) {
        log.info("{}", repImg);
        Member member = memberService.getMember();

        Category category = goodsService.getCategory(goodsSaveDto.getCategoryId());
        Goods goods = goodsSaveDto.toGoods(category);
        Sell sell = Sell.createSell(member, goods);

        goodsRepository.save(goods);
        sellRepository.save(sell);

        if (nonNull(imageList)) {
            goodsService.updateImage(imageList, goods.getId(), repImg);
        }

        if (isNull(imageList)) {
            goodsService.saveDefaultImage(goods);
        }

        return goods.getId();
    }

    public Long saveGoodsCoordinate(Long goodsId, GoodsCoordinateDto goodsCoordinateDto) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        GoodsCoordinate goodsCoordinate = goodsCoordinateDto.toGoodsCoordinate(goods);

        goodsCoordinateRepository.save(goodsCoordinate);

        return goodsCoordinate.getId();
    }
}
