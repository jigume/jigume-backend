package com.jigume.service.goods;

import com.jigume.dto.goods.*;
import com.jigume.entity.board.Board;
import com.jigume.entity.goods.Category;
import com.jigume.entity.goods.DefaultImgUrl;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.goods.GoodsImage;
import com.jigume.entity.member.Member;
import com.jigume.entity.order.Sell;
import com.jigume.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.*;
import com.jigume.service.member.MemberService;
import com.jigume.service.s3.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final GoodsImagesRepository goodsImagesRepository;
    private final S3FileUploadService s3FileUploadService;
    private final SellRepository sellRepository;
    private final BoardRepository boardRepository;

    public Long saveGoods(GoodsSaveDto goodsSaveDto) {
        log.info("{}", goodsSaveDto.getCategoryId());
        Category category = getCategory(goodsSaveDto.getCategoryId());

        Member member = memberService.getMember();

        Goods goods = Goods.createGoods(goodsSaveDto.getGoodsName(), goodsSaveDto.getIntroduction(),
                goodsSaveDto.getLink(), goodsSaveDto.getGoodsPrice(),
                goodsSaveDto.getDeliveryFee(), goodsSaveDto.getMapX(),
                goodsSaveDto.getMapY(), goodsSaveDto.getGoodsLimitCount(),
                goodsSaveDto.getGoodsLimitTime(), category);

        Board board = Board.createBoard(goodsSaveDto.getBoardContent(), goods);

        Long goodsId = goodsRepository.save(goods).getId();

        boardRepository.save(board);

        Sell sell = Sell.createSell(member, goods);

        sellRepository.save(sell);

        log.info("{}", goodsId);

        return goodsId;
    }

    public void endGoodsSelling(Long goodsId) {
        Member member = memberService.getMember();

        Goods goods = getGoods(goodsId);

        checkGoodsSeller(goods, member);

        goods.updateEnd();
    }

    public void updateImage(List<MultipartFile> imageList, Long goodsId, Integer repImg) {
        Goods goods = getGoods(goodsId);

        int i = 0;

        for (MultipartFile goodsImgFile : imageList) {
            String goodsImgUrl = s3FileUploadService.uploadFile(goodsImgFile);

            GoodsImage goodsImage;
            if (repImg != null && i == repImg) {
                goodsImage = GoodsImage.createGoodsImage(goods, goodsImgUrl, true);
            } else {
                goodsImage = GoodsImage.createGoodsImage(goods, goodsImgUrl, false);
            }
            goodsImagesRepository.save(goodsImage);

            i++;
        }

    }

    public GoodsDetailPageDto getGoodsPage(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = getGoods(goodsId);
        Member hostMember = goods.getSell().getMember();
        List<Sell> sellsByMemberId = sellRepository.findSellsByMemberId(hostMember.getId());

        checkTime(goods);

        boolean isOrderOrSell = isOrderOrSell(member, goods);

        return new GoodsDetailPageDto(isOrderOrSell,
                toGoodsPageDto(goods));
    }

    public List<GoodsDto> getGoodsList() {
        List<Goods> goodsList = goodsRepository.findAll();
        goodsList.forEach(this::checkTime);

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public List<GoodsDto> getGoodsList(List<Goods> goodsList) {
        goodsList.forEach(this::checkTime);

        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }


    public List<GoodsDto> getGoodsList(Long categoryId) {
        List<Goods> goodsList = goodsRepository.findAllByCategoryId(categoryId);
        goodsList.forEach(this::checkTime);


        List<GoodsDto> goodsDtoList = toGoodsDtoList(goodsList);

        return goodsDtoList;
    }

    public Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private void checkTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goods.updateEnd();
        }
    }

    public List<GoodsDto> toGoodsDtoList(List<Goods> goodsList) {
        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (Goods goods : goodsList) {
            Member hostMember = goods.getSell().getMember();
            int hostSellCount = sellRepository.findSellsByMemberId(hostMember.getId()).size();
            GoodsDto goodsDto = new GoodsDto().builder().goodsId(goods.getId()).goodsName(goods.getName())
                    .goodsStatus(goods.getGoodsStatus())
                    .goodsPrice(goods.getGoodsPrice())
                    .goodsOrderCount(goods.getCurrentOrderGoodsCount())
                    .goodsDeliveryPrice(goods.getDeliveryFee())
                    .hostNickName(hostMember.getNickname())
                    .hostSellCount(hostSellCount)
                    .repImgUrl(goodsImagesRepository.findGoodsImageByGoodsIdAndRepimgYn(goods.getId(), true)
                            .orElse(goodsImagesRepository.findDefalutGoodsImage(DefaultImgUrl.DEFAULT_GOODS_IMG_URL.getDefaultImgUrl()))
                            .getGoodsImgUrl()).build();

            goodsDtoList.add(goodsDto);
        }

        return goodsDtoList;
    }


    private GoodsPageDto toGoodsPageDto(Goods goods) {
        Member hostMember = goods.getSell().getMember();
        int hostSellCount = sellRepository.findSellsByMemberId(hostMember.getId()).size();

        GoodsPageDto goodsPageDto = new GoodsPageDto().builder()
                .goodsId(goods.getId()).goodsName(goods.getName())
                .introduction(goods.getIntroduction())
                .link(goods.getLink()).goodsPrice(goods.getGoodsPrice())
                .deliveryFee(goods.getDeliveryFee()).mapX(goods.getAddress().getMapX())
                .mapY(goods.getAddress().getMapY()).goodsLimitTime(goods.getGoodsLimitTime())
                .goodsLimitCount(goods.getGoodsLimitCount()).category(goods.getCategory().getId())
                .realDeliveryFee(goods.getRealDeliveryFee()).goodsStatus(goods.getGoodsStatus())
                .hostNickname(hostMember.getNickname()).hostSellCount(hostSellCount)
                .goodsOrderCount(goods.getCurrentOrderGoodsCount())
                .discountDeliveryPrice(goods.getDeliveryFee() - goods.getRealDeliveryFee())
                .goodsImagesList(GoodsImagesDto.toGoodsImagesDto(goods.getGoodsImageList()))
                .boardId(goods.getBoard().getId())
                .build();


        return goodsPageDto;
    }

    public boolean isOrderOrSell(Member member, Goods goods) {
        if (goods.getSell().getMember().equals(member)
                || goods.getOrderList().stream().filter(order -> order
                .getMember().equals(member)).findAny().isPresent()) {
            return true;
        }
        return false;
    }

    public void checkGoodsSeller(Goods goods, Member member) {
        if (goods.getSell().getMember() == member) {
            throw new AuthNotAuthorizationMemberException();
        }
    }
}
