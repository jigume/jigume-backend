package site.jigume.domain.order.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.dto.OrderDto;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.repository.OrderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommendService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsService goodsService;

    public void orderGoods(OrderDto orderDto) {
        Member member = memberService.getMember();
        Goods goods = goodsService.getGoods(orderDto.getGoodsId());

        Order order = Order.createBuyOrder(orderDto.getOrderGoodsCount(),
                goods, member);

        goods.updateGoodsOrder(order.getOrderGoodsCount());
        goods.getOrderList().stream().forEach(buyOrder -> buyOrder.updateOrderPrice(goods));

        orderRepository.save(order);
    }
}
