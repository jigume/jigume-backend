package site.jigume.domain.goods.service.commend;

import com.jigume.dto.board.BoardCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.goods.dto.GoodsSaveDto;
import site.jigume.domain.goods.entity.Category;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.entity.Sell;
import site.jigume.domain.order.repository.SellRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoodsCreateService {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final BoardRepository boardRepository;
    private final SellRepository sellRepository;

    public Long saveGoods(GoodsSaveDto goodsSaveDto, BoardCreateDto boardCreateDto,
                          List<MultipartFile> imageList, Integer repImg) {
        log.info("{}", repImg);
        Member member = memberService.getMember();

        Category category = goodsService.getCategory(goodsSaveDto.getCategoryId());
        Goods goods = goodsSaveDto.toGoods(category);
        Board board = Board.createBoard(boardCreateDto.getBoardContent(), goods);
        Sell sell = Sell.createSell(member, goods);

        goodsRepository.save(goods);

        goodsService.updateImage(imageList, goods.getId(), repImg);
        goods.initGoods(sell, board);
        boardRepository.save(board);
        sellRepository.save(sell);


        return goods.getId();
    }
}
