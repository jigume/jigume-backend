package com.jigume.service.order;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.order.EndBuyHistoryDto;
import com.jigume.dto.order.OrderDto;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.goods.GoodsStatus;
import com.jigume.entity.member.Member;
import com.jigume.entity.order.Order;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.GoodsRepository;
import com.jigume.repository.OrderRepository;
import com.jigume.service.goods.GoodsService;
import com.jigume.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final GoodsService goodsService;

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
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
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

        return goodsService.getGoodsList(processingGoodsList);
    }
}
