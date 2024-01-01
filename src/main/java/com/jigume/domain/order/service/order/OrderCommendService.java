package com.jigume.domain.order.service.order;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.OrderDto;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
