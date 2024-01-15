package site.jigume.domain.goods.controller.commend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.commend.GoodsDeleteService;
import site.jigume.domain.member.exception.auth.AuthException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsDeleteController {

    private final GoodsDeleteService goodsDeleteService;

    @Operation(summary = "Goods를 삭제한다.(주문이 없을 때만 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굿즈 판매 종료"),
            @ApiResponse(responseCode = "404", description = "굿즈를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "401", description = "해당 굿즈에 대한 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "400", description = "팔로워(주문한 사람)이 있어 삭제 할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @DeleteMapping("/{goodsId}")
    public ResponseEntity deleteGoods(@PathVariable("goodsId") Long goodsId) {
        goodsDeleteService.goodsDelete(goodsId);

        return new ResponseEntity(OK);
    }
}
