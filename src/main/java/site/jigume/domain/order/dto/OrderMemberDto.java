package site.jigume.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.order.entity.Order;
import site.jigume.domain.order.entity.OrderStatus;

@Data
@NoArgsConstructor
public class OrderMemberDto {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private Long orderId;
    private OrderStatus orderStatus;

    public static OrderMemberDto from(Order order, Member member) {
        OrderMemberDto orderMemberDto = new OrderMemberDto();
        orderMemberDto.memberId = member.getId();
        orderMemberDto.nickname = member.getNickname();
        orderMemberDto.profileImageUrl = member.getFile().getUrl();
        orderMemberDto.orderId = order.getId();
        orderMemberDto.orderStatus = order.getOrderStatus();

        return orderMemberDto;
    }
}
