package site.jigume.domain.goods.controller.query;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.goods.dto.MarkerListDto;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.query.GoodsMapQueryService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsMapController {

    private final GoodsMapQueryService goodsMapQueryService;

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
        MarkerListDto mapMarker = goodsMapQueryService
                .getMapMarker(minX, maxX, minY, maxY);

        return new ResponseEntity(mapMarker, OK);
    }
}
