package com.jigume.domain.order.service;

import com.jigume.domain.goods.dto.GoodsListDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.repository.GoodsImagesRepository;
import com.jigume.domain.goods.service.GoodsService;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.OrderDto;
import com.jigume.domain.order.dto.OrderHistoryDto;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.repository.OrderRepository;
import com.jigume.domain.order.repository.SellRepository;
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
    private final SellRepository sellRepository;
    private final GoodsImagesRepository goodsImagesRepository;
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

    public OrderHistoryDto getOrderEndHistory() {
        Member member = memberService.getMember();
        List<Order> findOrders = orderRepository
                .findOrdersByMemberIdAndGoodsGoodsStatus(member.getId(), GoodsStatus.END);

        List<GoodsListDto> goodsListDtoList = findOrders.stream()
                .map(order -> order.getGoods())
                .map(goods -> GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return new OrderHistoryDto(goodsListDtoList);
    }

    public OrderHistoryDto getOrderProcessingHistory() {
        Member member = memberService.getMember();
        List<Order> findOrders = orderRepository
                .findOrdersByMemberIdAndGoodsGoodsStatus(member.getId(), GoodsStatus.PROCESSING);

        List<GoodsListDto> goodsListDtoList = findOrders.stream()
                .map(order -> order.getGoods())
                .map(goods -> GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return new OrderHistoryDto(goodsListDtoList);
    }
}
