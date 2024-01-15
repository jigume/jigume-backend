package site.jigume.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

import static site.jigume.domain.order.dto.OrderMemberDto.toOrderMemberDto;

@Data
@NoArgsConstructor
public class OrderMemberListDto {

    private List<OrderMemberDto> orderMemberDtoList = new ArrayList<>();

    public static OrderMemberListDto toOrderMemberListDto(List<Order> orderList) {
        OrderMemberListDto orderMemberListDto = new OrderMemberListDto();

        orderMemberListDto.orderMemberDtoList = orderList.stream()
                .map(order -> order.getMember())
                .map(member -> toOrderMemberDto(member))
                .toList();

        return orderMemberListDto;
    }
}
