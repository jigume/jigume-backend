package site.jigume.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.jigume.domain.member.entity.Member;

@Data
@NoArgsConstructor
public class OrderMemberDto {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;

    public static OrderMemberDto from(Member member) {
        OrderMemberDto orderMemberDto = new OrderMemberDto();
        orderMemberDto.memberId = member.getId();
        orderMemberDto.nickname = member.getNickname();
        orderMemberDto.profileImageUrl = member.getFile().getUrl();

        return orderMemberDto;
    }
}
