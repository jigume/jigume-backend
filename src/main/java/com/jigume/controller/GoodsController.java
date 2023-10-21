package com.jigume.controller;

import com.jigume.dto.goods.GoodsBoardDto;
import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.goods.GoodsPageDto;
import com.jigume.dto.goods.GoodsSaveDto;
import com.jigume.entity.goods.ImageUploadRequest;
import com.jigume.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.service.goods.GoodsService;
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

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GoodsController {

    private final GoodsService goodsService;

    @Operation(summary = "이미지를 제외한 상품을 업로드 하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 및 리프레쉬 토큰 갱신", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsSaveDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/goods")
    public ResponseEntity saveGoods(@RequestBody GoodsSaveDto goodsSaveDto) {
        Long goodsId = goodsService.saveGoods(goodsSaveDto);

        return new ResponseEntity(goodsId, OK);
    }

    @Operation(summary = "상품 이미지 저장")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "이미지를 업로드 하려는 Goods의 PK", example = "1"),
            @Parameter(name = "repImg", description = "이미지가 대표 이미지인지", example = "true, false")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 저장 성공"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/{goodsId}/image")
    public ResponseEntity saveImage(ImageUploadRequest request,
                                    @PathVariable("goodsId") Long goodsId, @RequestParam("repImg") Boolean repImgYn) {
        goodsService.saveImage(request.multipartFile(), goodsId, repImgYn);

        return new ResponseEntity("이미지 저장 성공", OK);
    }

    @Operation(summary = "상품 상세 페이지를 반환한다.")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 PK", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 상세 페이지 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsBoardDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
    })
    @GetMapping("/goods/{goodsId}/page")
    public ResponseEntity getGoodsPage(@PathVariable("goodsId") Long goodsId) {
        GoodsBoardDto goodsPage = goodsService.getGoodsPage(goodsId);

        return new ResponseEntity(goodsPage, OK);
    }

    @Operation(summary = "맞는 카테고리의 GoodsList를 반환한다.")
    @Parameters(value = {
            @Parameter(name = "categoryId", description = "카테고리 Id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsPageDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
    })
    @GetMapping("/goods/{categoryId}/list")
    public ResponseEntity getGoodsList(@PathVariable("categoryId") Long categoryId) {
        List<GoodsDto> goodsList = goodsService.getGoodsList(categoryId);

        return new ResponseEntity(goodsList, OK);
    }

    @Operation(summary = "GoodsList를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsPageDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
    })
    @GetMapping("/goods")
    public ResponseEntity getGoodsList() {
        List<GoodsDto> goodsList = goodsService.getGoodsList();

        return new ResponseEntity(goodsList, OK);
    }


}
