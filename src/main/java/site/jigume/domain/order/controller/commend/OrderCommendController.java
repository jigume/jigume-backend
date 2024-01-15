package site.jigume.domain.order.controller.commend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.order.dto.OrderDto;
import site.jigume.domain.order.service.order.commend.OrderCreateService;
import site.jigume.domain.order.service.order.query.OrderQueryService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderCommendController {

    private final OrderCreateService orderCreateService;
    private final OrderQueryService orderQueryService;

    @Operation(summary = "주문(구매)하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문이 성공적으로 되었습니다."),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @PostMapping("/{goodsId}/new")
    public ResponseEntity newOrder(@RequestBody OrderDto orderDto) {
        orderCreateService.orderGoods(orderDto);

        return new ResponseEntity("주문이 성공적으로 되었습니다.", OK);
    }
}
