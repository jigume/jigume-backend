package com.jigume.domain.goods.controller;

import com.jigume.domain.goods.dto.GoodsSaveDto;
import com.jigume.domain.goods.exception.CategoryNotFoundException;
import com.jigume.domain.goods.exception.GoodsNotFoundException;
import com.jigume.domain.goods.service.GoodsCommendService;
import com.jigume.domain.member.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.domain.member.exception.auth.exception.AuthNotAuthorizationMemberException;
import com.jigume.global.aws.s3.exception.exception.S3InvalidImageException;
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
@RequestMapping("/api/goods")
public class GoodsCommendController {

    private final GoodsCommendService goodsCommendService;

    @Operation(summary = "이미지를 포함한 상품을 업로드 하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 저장 성공, goodsId 반환"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryNotFoundException.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 이미지", content = @Content(mediaType = "application/json", schema = @Schema(implementation = S3InvalidImageException.class)))
    })
    @PostMapping(value = "/new",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveGoods(@RequestPart GoodsSaveDto goodsSaveDto, @RequestPart(value = "images", required = false) List<MultipartFile> imageList,
                                    @RequestParam(name = "repImg", required = false) Integer repImg) {
        Long goodsId = goodsCommendService.saveGoods(goodsSaveDto, imageList, repImg);

        return new ResponseEntity(goodsId, OK);
    }

    @Operation(summary = "Goods 판매를 끝낸다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 판매 종료"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthNotAuthorizationMemberException.class)))
    })
    @PostMapping("/{goodsId}/end")
    public ResponseEntity endGoodsSelling(@PathVariable Long goodsId) {
        goodsCommendService.endGoodsSelling(goodsId);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "Goods 설명을 업데이트 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 업데이트를 성공적으로 했음"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthNotAuthorizationMemberException.class)))
    })
    @PostMapping("/{goodsId}/intro")
    public ResponseEntity updateGoodsIntroduction(@PathVariable Long goodsId,
                                                  @RequestBody String introduction) {
        goodsCommendService.updateGoodsIntroduction(goodsId, introduction);

        return new ResponseEntity(OK);
    }
}
