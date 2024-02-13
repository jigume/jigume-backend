package site.jigume.domain.order.controller.commend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.order.dto.OrderDto;
import site.jigume.domain.order.exception.order.OrderException;
import site.jigume.domain.order.service.order.commend.OrderCreateService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{goodsId}/order")
public class OrderCommendController {

    private final OrderCreateService orderCreateService;

    @Operation(summary = "주문(구매)하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문이 성공적으로 되었습니다."),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @PostMapping("/new")
    public ResponseEntity newOrder(@RequestBody OrderDto orderDto) {
        orderCreateService.orderGoods(orderDto);

        return new ResponseEntity("주문이 성공적으로 되었습니다.", OK);
    }

    @Operation(summary = "사용자가 구매를 확정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문이 성공적으로 되었습니다."),
            @ApiResponse(responseCode = "400", description = "인가된 사용자가 아닙니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "주문을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderException.class)))
    })
    @PostMapping("/{orderId}/confirmation")
    public ResponseEntity confirmOrder(@PathVariable Long goodsId,
                                       @PathVariable Long orderId) {
        orderCreateService.confirmOrder(goodsId, orderId);

        return new ResponseEntity(OK);
    }
}
