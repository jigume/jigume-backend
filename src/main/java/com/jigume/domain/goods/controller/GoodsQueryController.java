package com.jigume.domain.goods.controller;

import com.jigume.domain.goods.dto.*;
import com.jigume.domain.goods.exception.GoodsException;
import com.jigume.domain.goods.service.GoodsQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsQueryController {

    private final GoodsQueryService goodsQueryService;

    @Operation(summary = "상품 상세 페이지를 반환한다.")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "Goods의 PK", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 상세 페이지 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsDetailPageDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/{goodsId}/page")
    public ResponseEntity getGoodsPage(@PathVariable("goodsId") Long goodsId) {
        GoodsDetailPageDto goodsPage = goodsQueryService.getGoodsPage(goodsId);

        return new ResponseEntity(goodsPage, OK);
    }

    @Operation(summary = "범위에 속하고 카테고리 분류가 같은 GoodsList를 반환한다.")
    @Parameters(value = {
            @Parameter(name = "categoryId", description = "카테고리 Id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/{categoryId}/list")
    public ResponseEntity getGoodsList(@PathVariable("categoryId") Long categoryId,
                                       @RequestParam("minX") double minX,
                                       @RequestParam("maxX") double maxX,
                                       @RequestParam("minY") double minY,
                                       @RequestParam("maxY") double maxY,
                                       Pageable pageable) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setMaxPoint(new Point(maxX, maxY));
        coordinateDto.setMinPoint(new Point(minX, minY));

        GoodsSliceDto goodsSliceDto = goodsQueryService.getGoodsListByCategoryId(categoryId, coordinateDto, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }

    @Operation(summary = "해당 범위의 상품들을 모두 반환한다. 바텀시트 올릴 시")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/list")
    public ResponseEntity getGoodsList(@RequestParam("minX") double minX,
                                       @RequestParam("maxX") double maxX,
                                       @RequestParam("minY") double minY,
                                       @RequestParam("maxY") double maxY,
                                       Pageable pageable) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setMaxPoint(new Point(maxX, maxY));
        coordinateDto.setMinPoint(new Point(minX, minY));

        GoodsSliceDto goodsSliceDto = goodsQueryService.getGoodsList(coordinateDto, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }

    @Operation(summary = "해당 지도 범위의 상품들을 모두 반환한다 - 마커용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkerListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/marker/list")
    public ResponseEntity getGoodsMarker(@RequestParam("minX") double minX,
                                         @RequestParam("maxX") double maxX,
                                         @RequestParam("minY") double minY,
                                         @RequestParam("maxY") double maxY) {
        MarkerListDto mapMarker = goodsQueryService.getMapMarker(minX, maxX, minY, maxY);

        return new ResponseEntity(mapMarker, OK);
    }

    @Operation(summary = "해당 마커의 상품들을 전부 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/marker")
    public ResponseEntity getMarkerGoods(@RequestParam List<Long> goodsIds, Pageable pageable) {
        GoodsSliceDto goodsSliceDto = goodsQueryService.getMarkerGoods(goodsIds, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }
}
