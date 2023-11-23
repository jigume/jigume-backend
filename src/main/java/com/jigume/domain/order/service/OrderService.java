package com.jigume.domain.order.service;

import com.jigume.domain.goods.dto.GoodsDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.goods.service.GoodsQueryService;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.EndBuyHistoryDto;
import com.jigume.domain.order.dto.OrderDto;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.repository.OrderRepository;
import com.jigume.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final GoodsService goodsService;
    private final GoodsQueryService goodsQueryService;

    public void orderGoods(OrderDto orderDto) {
        Member member = memberService.getMember();
        Goods goods = getGoods(orderDto.getGoodsId());

        Order order = Order.createBuyOrder(orderDto.getOrderGoodsCount(),
                goods, member);

        goods.updateGoodsOrder(order.getOrderGoodsCount());
        goods.getOrderList().stream().forEach(buyOrder -> buyOrder.updateOrderPrice(goods));

        orderRepository.save(order);
    }


    private Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<EndBuyHistoryDto> getOrderEndHistory() {
        Member member = memberService.getMember();

        List<Order> ordersByMemberId = orderRepository.findOrdersByMemberId(member.getId());

        List<Order> endOrderHistory = ordersByMemberId.stream().filter(order -> order.getGoods().getGoodsStatus() == GoodsStatus.END)
                .collect(Collectors.toList());


        List<EndBuyHistoryDto> endBuyHistoryDtoList = endOrderHistory.stream().map(order -> EndBuyHistoryDto.builder()
                .boardId(order.getGoods().getBoard().getId())
                .goodsPrice(order.getGoods().getGoodsPrice())
                .goodsBuyCount(order.getOrderGoodsCount())
                .deliveryFee(order.getGoods().getDeliveryFee())
                .realDeliveryFee(order.getGoods().getRealDeliveryFee())
                .totalFee(order.getOrderPrice()).build()).collect(Collectors.toList());

        return endBuyHistoryDtoList;
    }

    public List<GoodsDto> getOrderProcessingHistory() {
        Member member = memberService.getMember();

        List<Order> ordersByMemberId = orderRepository.findOrdersByMemberId(member.getId());

        List<Order> processingOrderHistory = ordersByMemberId.stream().filter(order -> order.getGoods().getGoodsStatus() == GoodsStatus.PROCESSING)
                .collect(Collectors.toList());

        List<Goods> processingGoodsList = processingOrderHistory.stream().map(Order::getGoods).collect(Collectors.toList());

        return goodsQueryService.getGoodsList(processingGoodsList);
    }
}
