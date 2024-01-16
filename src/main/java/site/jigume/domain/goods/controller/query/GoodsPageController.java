package site.jigume.domain.goods.controller.query;

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
import site.jigume.domain.goods.dto.*;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.query.GoodsPageQueryService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsPageController {

    private final GoodsPageQueryService goodsPageQueryService;

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
        GoodsDetailPageDto goodsPage = goodsPageQueryService.getGoodsDetailPage(goodsId);

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

        GoodsSliceDto goodsSliceDto = goodsPageQueryService
                .getGoodsListByCategoryIdInMap(categoryId, coordinateDto, pageable);

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

        GoodsSliceDto goodsSliceDto = goodsPageQueryService
                .getGoodsListInMap(coordinateDto, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }

    @Operation(summary = "상품 Id 리스트 안의 상품들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/marker")
    public ResponseEntity getMarkerGoods(@RequestParam List<Long> goodsIds, Pageable pageable) {
        GoodsSliceDto goodsSliceDto = goodsPageQueryService.getGoodsListInIds(goodsIds, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }
}