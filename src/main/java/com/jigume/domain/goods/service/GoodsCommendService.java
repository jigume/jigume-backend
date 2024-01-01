package com.jigume.domain.goods.service;

import com.jigume.domain.board.entity.Board;
import com.jigume.domain.board.service.BoardService;
import com.jigume.domain.goods.dto.GoodsSaveDto;
import com.jigume.domain.goods.entity.Category;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.AuthException;
import com.jigume.domain.member.exception.auth.AuthExceptionCode;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.entity.Sell;
import com.jigume.domain.order.service.sell.SellCommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoodsCommendService {

    private final GoodsRepository goodsRepository;
    private final MemberService memberService;
    private final SellCommendService sellCommendService;
    private final BoardService boardService;
    private final GoodsService goodsService;

    public Long saveGoods(GoodsSaveDto goodsSaveDto, List<MultipartFile> imageList, Integer repImg) {
        log.info("{}", repImg);
        Member member = memberService.getMember();

        Category category = goodsService.getCategory(goodsSaveDto.getCategoryId());
        Goods goods = Goods.createGoods(goodsSaveDto.getGoodsName(), goodsSaveDto.getIntroduction(),
                goodsSaveDto.getLink(), goodsSaveDto.getGoodsPrice(),
                goodsSaveDto.getDeliveryFee(), goodsSaveDto.getMapX(),
                goodsSaveDto.getMapY(), goodsSaveDto.getGoodsLimitCount(),
                goodsSaveDto.getGoodsLimitTime(), category);

        Long goodsId = goodsRepository.save(goods).getId();
        Board board = boardService.createBoard(goodsSaveDto.getBoardContent(), goods);
        goods.setBoard(board);
        Sell sell = sellCommendService.createSell(member, goods);
        goods.setSell(sell);
        goodsService.updateImage(imageList, goodsId, repImg);


        return goodsId;
    }

    public void endGoodsSelling(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        checkGoodsSeller(goods, member);

        goods.updateEnd();
    }

    public void updateGoodsIntroduction(Long goodsId, String introduction) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(goodsId);

        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }

        goods.updateGoodsIntroduction(introduction);
    }

    private void checkGoodsSeller(Goods goods, Member member) {
        if (!goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }
}
