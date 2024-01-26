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
import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.dto.MarkerDto;
import site.jigume.domain.goods.dto.coordinate.CoordinateRequestDto;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.query.GoodsCoordinateQueryService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsCoordinateQueryController {

    private final GoodsCoordinateQueryService goodsCoordinateQueryService;

    @Operation(summary = "해당 지도 범위의 상품들을 모두 반환한다 - 마커용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkerDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/marker/list")
    public ResponseEntity getGoodsCoordinateMarker(@RequestParam CoordinateRequestDto coordinateRequestDto) {
        List<MarkerDto> mapMarker = goodsCoordinateQueryService.getMapMarker(coordinateRequestDto);

        return new ResponseEntity(mapMarker, OK);
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
                                       @RequestParam CoordinateRequestDto coordinateRequestDto,
                                       Pageable pageable) {
        GoodsSliceDto goodsSliceDto = goodsCoordinateQueryService
                .getGoodsListByCategoryIdInMap(categoryId, coordinateRequestDto, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }

    @Operation(summary = "해당 범위의 상품들을 모두 반환한다. 바텀시트 올릴 시")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/list")
    public ResponseEntity getGoodsList(@RequestParam CoordinateRequestDto coordinateRequestDto,
                                       Pageable pageable) {

        GoodsSliceDto goodsSliceDto = goodsCoordinateQueryService
                .getGoodsListInMap(coordinateRequestDto, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }
}
