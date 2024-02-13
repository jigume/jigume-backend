package site.jigume.domain.goods.controller.commend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.goods.dto.GoodsLimitTimeDto;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.commend.GoodsUpdateService;
import site.jigume.domain.member.exception.auth.AuthException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods/{goodsId}")
public class GoodsUpdateController {

    private final GoodsUpdateService goodsUpdateService;

    @Operation(summary = "Goods 판매를 끝낸다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 판매 종료"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/end")
    public ResponseEntity endGoodsSelling(@PathVariable Long goodsId) {
        goodsUpdateService.endGoodsSelling(goodsId);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "Goods 설명을 업데이트 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 업데이트를 성공적으로 했음"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/intro")
    public ResponseEntity updateGoodsIntroduction(@PathVariable Long goodsId,
                                                  @RequestBody String introduction) {
        goodsUpdateService.updateGoodsIntroduction(goodsId, introduction);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "Goods 좌표를 업데이트 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 좌표 업데이트를 성공적으로 했음"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/coordinate")
    public ResponseEntity updateGoodsCoordinate(@PathVariable Long goodsId,
                                                @RequestBody GoodsCoordinateDto goodsCoordinateDto) {
        Long goodsCoordinateId = goodsUpdateService.updateGoodsCoordinate(goodsId, goodsCoordinateDto);

        return new ResponseEntity(goodsCoordinateId, OK);
    }

    @Operation(summary = "Goods 상태를 완료로 바꾼다.(모든 구매자가 구매 확정을 완료해야 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료로 성공적으로 바꿈"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "400", description = "모든 구매자가 구매 확정을 하지 않았습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/finished")
    public ResponseEntity finishGoods(@PathVariable Long goodsId) {
        goodsUpdateService.finishGoods(goodsId);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "상품의 제한 시간을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/time")
    public ResponseEntity updateLimitTime(@PathVariable Long goodsId,
                                          @RequestBody GoodsLimitTimeDto goodsLimitTimeDto) {
        goodsUpdateService.updateGoodsLimitTime(goodsId, goodsLimitTimeDto);

        return new ResponseEntity(OK);
    }
}
