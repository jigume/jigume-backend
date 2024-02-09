package site.jigume.domain.order.controller.query;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.goods.entity.GoodsStatus;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.order.dto.OrderHistoryDto;
import site.jigume.domain.order.dto.OrderInfo;
import site.jigume.domain.order.dto.OrderInfoList;
import site.jigume.domain.order.dto.OrderMemberDto;
import site.jigume.domain.order.service.order.query.OrderQueryService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    @Operation(summary = "회원의 주문 내역을 가져오는 API")
    @Parameters(value = {
            @Parameter(name = "goodsStatus", description = "Goods의 상태", example = "PROCESSING, END"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 회원의 주문 리스트를 가져옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderHistoryDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @GetMapping("/order/{status}")
    public ResponseEntity getOrderHistory(@PathVariable("status") GoodsStatus goodsStatus) {
        OrderHistoryDto orderHistory = orderQueryService.getOrderHistory(goodsStatus);

        return new ResponseEntity(orderHistory, OK);
    }

    @Operation(summary = "상품의 전체 주문을 가져오는 API(판매자용 -> 전체 주문을 조회하고 얼마를 주문해야 할지 받는다)")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 Id", example = "1, 2, 3"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderInfo를 ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoList.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/goods/{goodsId}/order/list")
    public ResponseEntity getOrderInfoList(@PathVariable("goodsId") Long goodsId) {
        OrderInfoList orderInfoList = orderQueryService.getOrderInfoList(goodsId);

        return new ResponseEntity(orderInfoList, OK);
    }

    @Operation(summary = "구매자의 예상 결제 금액을 가져오는 API")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 Id", example = "1, 2, 3"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매자의 예상 결제 금액을 가져옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoList.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/goods/{goodsId}/order")
    public ResponseEntity getOrderInfo(@PathVariable("goodsId") Long goodsId) {
        OrderInfo orderInfo = orderQueryService.getOrder(goodsId);

        return new ResponseEntity(orderInfo, OK);
    }

    @Operation(summary = "구매 참여자의 목록을 가져옴")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 Id", example = "1, 2, 3"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매 참여자 목록을 가져온다. (리스트 형식)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderMemberDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/goods/{goodsId}/order/members")
    public ResponseEntity getOrderMemberList(@PathVariable("goodsId") Long goodsId) {
        List<OrderMemberDto> orderMemberList = orderQueryService.getOrderMemberList(goodsId);

        return new ResponseEntity(orderMemberList, OK);
    }
}
