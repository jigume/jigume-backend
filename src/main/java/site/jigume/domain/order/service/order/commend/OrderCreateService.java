package site.jigume.domain.order.service.order.commend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.dto.OrderDto;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.repository.OrderRepository;

import java.time.LocalDateTime;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.CATEGORY_NOT_FOUND;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_END_TIME;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    public void orderGoods(OrderDto orderDto) {
        Member member = memberService.getMember();
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(orderDto.getGoodsId())
                .orElseThrow(() -> new GoodsException(CATEGORY_NOT_FOUND));

        if (checkEndTime(goods)) {
            throw new GoodsException(GOODS_END_TIME);
        }

        Order order = Order.createOrder(orderDto.getOrderGoodsCount(),
                goods, member);

        goods.updateCurrentOrderCount();

        orderRepository.save(order);
    }

    private boolean checkEndTime(Goods goods) {
        LocalDateTime now = LocalDateTime.now();

        if (!goods.getGoodsLimitTime().isAfter(now)) {
            timeEnd(goods);
            return true;
        }

        return false;
    }

    public void timeEnd(Goods goods) {
        goods.updateEnd();
    }
}
