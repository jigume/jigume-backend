package com.jigume.domain.order.controller;

import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.member.exception.auth.AuthException;
import com.jigume.domain.order.dto.SellHistoryDto;
import com.jigume.domain.order.service.sell.SellQueryService;
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

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SellController {

    private final SellQueryService sellQueryService;

    @Operation(summary = "회원의 판매 내역을 가져오는 API")
    @Parameters(value = {
            @Parameter(name = "goodsStatus", description = "Goods의 상태", example = "PROCESSING, END"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 회원의 판매 리스트를 가져옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SellHistoryDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @GetMapping("/sell/{status}")
    public ResponseEntity getSellHistory(@PathVariable("status") GoodsStatus status) {
        SellHistoryDto sellHistoryDto = sellQueryService.getSellHistory(status);
        return new ResponseEntity(sellHistoryDto, OK);
    }
}
