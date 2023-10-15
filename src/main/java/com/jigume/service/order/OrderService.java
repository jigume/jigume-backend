package com.jigume.service.order;

import com.jigume.dto.order.DoneDto;
import com.jigume.dto.order.OrderDto;
import com.jigume.dto.order.ProgressDto;
import com.jigume.entity.member.Member;
import com.jigume.entity.board.Board;
import com.jigume.entity.goods.Goods;
import com.jigume.entity.order.Order;
import com.jigume.entity.order.OrderStatus;
import com.jigume.entity.order.OrderType;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.BoardRepository;
import com.jigume.repository.GoodsRepository;
import com.jigume.repository.OrderRepository;
import com.jigume.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;
    private final BoardRepository boardRepository;

    public void orderGoods(String memberIdx, Long goodsId, OrderDto orderDto) {
        Member member = memberService.getMember();
        Goods goods = getGoods(goodsId);
        List<Order> ordersByGoodsId = orderRepository.findOrdersByGoodsId(goodsId);

        Order order = Order.createOrder(orderDto.getOrderCount(), OrderType.getOrderType(orderDto.getOrderType()), goods, member, ordersByGoodsId.size());

        goods.updateCurrentOrder(order.getOrderCount());

        orderRepository.save(order);
    }

    public List<ProgressDto> getProgressOrderList(String memberIdx, Integer orderTypeNum, Integer orderStatusNum) {
        Member member = memberService.getMember();

        OrderStatus orderStatus = OrderStatus.getOrderStatus(orderStatusNum);
        OrderType orderType = OrderType.getOrderType(orderTypeNum);

        List<ProgressDto> progressDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByMember_IdAndOrderTypeAndOrderStatus(member.getId(), orderType, orderStatus);
        for (Order order : orderList) {
            Goods goods = order.getGoods();
            Board board = boardRepository.findBoardByGoodsId(goods.getId());
            Integer buyCase = orderRepository.findOrdersByGoodsId(goods.getId()).size();
            ProgressDto progressDto = ProgressDto.toProgressDto(board.getId(), buyCase, goods.getHostNickname());

            progressDtoList.add(progressDto);
        }

        return progressDtoList;
    }

    public List<DoneDto> getDoneOrderList(String memberIdx, Integer orderTypeNum, Integer orderStatusNum) {
        Member member = memberService.getMember();

        OrderStatus orderStatus = OrderStatus.getOrderStatus(orderStatusNum);
        OrderType orderType = OrderType.getOrderType(orderTypeNum);

        List<DoneDto> doneDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByMember_IdAndOrderTypeAndOrderStatus(member.getId(), orderType, orderStatus);
        for (Order order : orderList) {
            Goods goods = order.getGoods();
            Integer buyCase = orderRepository.findOrdersByGoodsId(goods.getId()).size();
            Integer buyCount = order.getOrderCount();
            DoneDto doneDto = DoneDto.toDoneDto(goods.getGoodsPrice(), goods.getDeliveryFee(),
                    buyCount,buyCase);

            doneDtoList.add(doneDto);
        }

        return doneDtoList;
    }

    private Goods getGoods(Long goodsId) {
        return goodsRepository.findGoodsById(goodsId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
