package com.jigume.service.goods;

import com.jigume.dto.goods.GoodsBoardDto;
import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.goods.GoodsSaveDto;
import com.jigume.entity.member.Member;
import com.jigume.entity.goods.Category;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.goods.GoodsImages;
import com.jigume.entity.order.Order;
import com.jigume.entity.order.OrderType;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.*;
import com.jigume.service.MemberService;
import com.jigume.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final BoardService boardService;
    private final GoodsImagesRepository goodsImagesRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long saveGoods(GoodsSaveDto goodsSaveDto, String memberIdx){
        GoodsDto goodsDto = goodsSaveDto.getGoodsDto();
        Category category = getCategory(goodsDto);

        Goods goods = Goods.createGoods(goodsDto.getName(), goodsDto.getIntroduction(), goodsDto.getLink(), goodsDto.getGoodsPrice(), goodsDto.getDeliveryFee(), goodsDto.getMapX(), goodsDto.getMapY(), goodsDto.getGoodsLimitCount(), goodsDto.getGoodsLimitTime(), memberService.getMember(memberIdx).getNickname(), category);

        boardService.createBoard(goodsSaveDto.getBoardContent(), goods);

        Long goodsId = goodsRepository.save(goods).getId();

        return goodsId;
    }

    public void saveImage(MultipartFile goodsImgFile, Long goodsId) throws IOException {
        Goods goods = getGoods(goodsId);

            GoodsImages goodsImages = new GoodsImages();
            goodsImages.setGoods(goods);
            goodsImages.setImage(goodsImgFile.getBytes());
            goodsImages.setRepimgYn(true);


            goodsImagesRepository.save(goodsImages);

    }

    public GoodsBoardDto getGoodsPage(Long goodsId) {
        Goods goods = getGoods(goodsId);
        String hostNickname = goods.getHostNickname();
        Member findMember = memberRepository.findMemberByNickname(hostNickname).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        List<Order> sellOrderList = orderRepository.findOrdersByMember_IdAndOrderType(findMember.getId(), OrderType.SELL);

        checkTime(goods);

        return new GoodsBoardDto(hostNickname, sellOrderList.size(), goods.getOrderList().size() + 1, GoodsDto.toGoodsDto(goods));
    }

    public List<GoodsDto> getGoodsList() {
        List<Goods> goodsList = goodsRepository.findAll();
        goodsList.forEach(this::checkTime);


        return goodsList.stream().map(GoodsDto::toGoodsDto).collect(Collectors.toList());
    }

    public List<GoodsDto> getGoodsList(Long categoryId) {
        List<Goods> goodsList = goodsRepository.findAllByCategoryId(categoryId);
        goodsList.forEach(this::checkTime);


        return goodsList.stream().map(GoodsDto::toGoodsDto).collect(Collectors.toList());
    }

    public Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Category getCategory(GoodsDto goodsDto) {
        return categoryRepository.findById(goodsDto.getCategory()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private void checkTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            goods.updateIsEnd(true);
        }
    }
}
