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
import site.jigume.domain.goods.dto.GoodsDetailPageDto;
import site.jigume.domain.goods.dto.GoodsListDto;
import site.jigume.domain.goods.dto.GoodsSliceDto;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.query.GoodsQueryService;

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
        GoodsDetailPageDto goodsPage = goodsQueryService.getGoodsDetailPage(goodsId);

        return new ResponseEntity(goodsPage, OK);
    }

    @Operation(summary = "상품 Id 리스트 안의 상품들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 리스트 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsListDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리소스를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
    })
    @GetMapping("/marker")
    public ResponseEntity getMarkerGoods(@RequestParam List<Long> goodsIds, Pageable pageable) {
        GoodsSliceDto goodsSliceDto = goodsQueryService.getGoodsListInIds(goodsIds, pageable);

        return new ResponseEntity(goodsSliceDto, OK);
    }
}
