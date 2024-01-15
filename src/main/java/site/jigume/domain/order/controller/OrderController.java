package site.jigume.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.order.dto.OrderDto;
import site.jigume.domain.order.dto.OrderHistoryDto;
import site.jigume.domain.order.dto.OrderInfoList;
import site.jigume.domain.order.service.order.commend.OrderCreateService;
import site.jigume.domain.order.service.order.query.OrderQueryService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

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

    @Operation(summary = "회원의 주문 내역을 가져오는 API")
    @Parameters(value = {
            @Parameter(name = "goodsStatus", description = "Goods의 상태", example = "PROCESSING, END"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 회원의 주문 리스트를 가져옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderHistoryDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @GetMapping("/orders/{status}")
    public ResponseEntity getOrderHistory(@PathVariable("status") GoodsStatus goodsStatus) {
        OrderHistoryDto orderHistory = orderQueryService.getOrderHistory(goodsStatus);

        return new ResponseEntity(orderHistory, OK);
    }

    @Operation(summary = "상품의 주문 내역을 가져오는 API")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 Id", example = "1, 2, 3"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderInfo를 ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoList.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/orders/{goodsId}/list")
    public ResponseEntity getOrderInfoList(@PathVariable("goodsId") Long goodsId) {
        OrderInfoList orderInfoList = orderQueryService.getOrderInfoList(goodsId);

        return new ResponseEntity(orderInfoList, OK);
    }
}
