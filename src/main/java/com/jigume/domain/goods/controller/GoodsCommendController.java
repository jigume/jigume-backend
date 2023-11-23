package com.jigume.domain.goods.controller;

import com.jigume.domain.goods.dto.GoodsSaveDto;
import com.jigume.domain.goods.service.GoodsCommendService;
import com.jigume.domain.member.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.global.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GoodsCommendController {

    private final GoodsCommendService goodsCommendService;

    @Operation(summary = "이미지를 포함한 상품을 업로드 하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 저장 성공, goodsId 반환"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping(value = "/goods",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveGoods(@RequestPart GoodsSaveDto goodsSaveDto, @RequestPart(value = "images", required = false) List<MultipartFile> imageList,
                                    @RequestPart("repImg") int repImg) {
        Long goodsId = goodsCommendService.saveGoods(goodsSaveDto, imageList, repImg);

        return new ResponseEntity(goodsId, OK);
    }

    @Operation(summary = "Goods 판매를 끝낸다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 판매 종료"),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
    })
    @PostMapping("/goods/{goodsId}/end")
    public ResponseEntity endGoodsSelling(@PathVariable Long goodsId) {
        goodsCommendService.endGoodsSelling(goodsId);

        return new ResponseEntity(OK);
    }
}
