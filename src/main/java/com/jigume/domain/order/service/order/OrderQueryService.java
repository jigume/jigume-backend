package com.jigume.domain.order.service.order;

import com.jigume.domain.goods.dto.GoodsListDto;
import com.jigume.domain.goods.entity.Goods;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.goods.exception.GoodsException;
import com.jigume.domain.goods.repository.GoodsRepository;
import com.jigume.domain.member.entity.Member;
import com.jigume.domain.member.exception.auth.AuthException;
import com.jigume.domain.member.service.MemberService;
import com.jigume.domain.order.dto.OrderHistoryDto;
import com.jigume.domain.order.dto.OrderInfo;
import com.jigume.domain.order.dto.OrderInfoList;
import com.jigume.domain.order.entity.Order;
import com.jigume.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;
import static com.jigume.domain.member.exception.auth.AuthExceptionCode.NOT_AUTHORIZATION_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final GoodsRepository goodsRepository;

    public OrderHistoryDto getOrderHistory(GoodsStatus goodsStatus) {
        Member member = memberService.getMember();
        List<Order> findOrders = orderRepository
                .findOrdersByMemberIdAndGoodsGoodsStatus(member.getId(), goodsStatus);

        List<GoodsListDto> goodsListDtoList = findOrders.stream()
                .map(order -> order.getGoods())
                .map(goods -> GoodsListDto.toGoodsListDto(goods))
                .collect(Collectors.toList());

        return new OrderHistoryDto(goodsListDtoList);
    }

    public OrderInfoList getOrderInfoList(Long goodsId) {
        Member member = memberService.getMember();

        Goods goods = goodsRepository.findGoodsByIdFetchJoinOrderList(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));

        if (goods.isSell(member)) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }

        List<OrderInfo> orderInfoList = goods.getOrderList().stream()
                .map(order -> OrderInfo.toOrderInfo(order))
                .collect(Collectors.toList());

        return new OrderInfoList(orderInfoList);
    }
}
