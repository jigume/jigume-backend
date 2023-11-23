package com.jigume.domain.goods.service;

import com.jigume.domain.board.service.BoardService;
import com.jigume.domain.goods.dto.GoodsSaveDto;
import com.jigume.domain.goods.entity.Category;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.service.SellService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsCommendService {

    private final GoodsRepository goodsRepository;
    private final MemberService memberService;
    private final SellService sellService;
    private final BoardService boardService;
    private final GoodsService goodsService;

    @Transactional
    public Long saveGoods(GoodsSaveDto goodsSaveDto, List<MultipartFile> imageList, int repImg) {
        Member member = memberService.getMember();

        Category category = goodsService.getCategory(goodsSaveDto.getCategoryId());
        Goods goods = Goods.createGoods(goodsSaveDto.getGoodsName(), goodsSaveDto.getIntroduction(),
                goodsSaveDto.getLink(), goodsSaveDto.getGoodsPrice(),
                goodsSaveDto.getDeliveryFee(), goodsSaveDto.getMapX(),
                goodsSaveDto.getMapY(), goodsSaveDto.getGoodsLimitCount(),
                goodsSaveDto.getGoodsLimitTime(), category);

        Long goodsId = goodsRepository.save(goods).getId();
        boardService.createBoard(goodsSaveDto.getBoardContent(), goods);
        sellService.createSell(member, goods);

        if (imageList.size() != 0) {
            goodsService.updateImage(imageList, goodsId, repImg);
        }

        return goodsId;
    }

    @Transactional
    public void endGoodsSelling(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkGoodsSeller(goods, member);

        goods.updateEnd();
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (goods.getSell().getMember() == member) {
            throw new AuthNotAuthorizationMemberException();
        }
    }
}
