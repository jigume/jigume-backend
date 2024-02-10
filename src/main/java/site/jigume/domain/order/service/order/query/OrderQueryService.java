package site.jigume.domain.order.service.order.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.MemberService;
import site.jigume.domain.order.dto.OrderHistoryDto;
import site.jigume.domain.order.dto.OrderInfo;
import site.jigume.domain.order.dto.OrderInfoList;
import site.jigume.domain.order.dto.OrderMemberDto;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;
import static site.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    public List<OrderHistoryDto> getOrderHistory(GoodsStatus goodsStatus) {
        Member member = memberService.getMember();
        List<Order> findOrders = orderRepository
                .findOrdersByMemberIdAndGoodsGoodsStatus(member.getId(), goodsStatus);

        return findOrders.stream()
                .map(order -> OrderHistoryDto.from(order))
                .collect(Collectors.toList());
    }

    public OrderInfo getOrderInfoBeforePay(Long goodsId, Integer orderGoodsCount) {
        Member member = memberService.getMember();

        Order order = Order.createOrder(orderGoodsCount, getGoods(goodsId), member);

        return OrderInfo.toOrderInfo(order);
    }

    public OrderInfo getOrder(Long goodsId) {
        Member member = memberService.getMember();

        Order order = orderRepository.findOrderByMemberIdAndGoodsId(member.getId(), goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        return OrderInfo.toOrderInfo(order);
    }

    public OrderInfoList getOrderInfoList(Long goodsId) {
        Member member = memberService.getMember();

        Goods goods = getGoodsWithOrderList(goodsId);

        if (goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        List<OrderInfo> orderInfoList = goods.getOrderList().stream()
                .map(order -> OrderInfo.toOrderInfo(order))
                .collect(Collectors.toList());

        return new OrderInfoList(orderInfoList);
    }

    public List<OrderMemberDto> getOrderMemberList(Long goodsId) {
        Member member = memberService.getMember();
        Goods goods = getGoodsWithOrderList(goodsId);

        goods.getOrderList().stream()
                .filter(order -> order.getMember().equals(member))
                .findAny()
                .orElseThrow(() -> new AuthException(NOT_AUTHORIZATION_USER));

        return goods.getOrderList().stream()
                .map(order -> order.getMember())
                .map(m -> OrderMemberDto.from(member))
                .toList();
    }

    private Goods getGoodsWithOrderList(Long goodsId) {
        Goods goods = goodsRepository.findGoodsByIdWithOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
        return goods;
    }

    private Goods getGoods(Long goodsId) {
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
    }
}
