package site.jigume.domain.goods.controller.commend;

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
import site.jigume.domain.goods.dto.GoodsSaveDto;
import site.jigume.domain.goods.dto.coordinate.GoodsCoordinateDto;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.service.commend.GoodsCreateService;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.global.aws.s3.exception.exception.S3InvalidImageException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsCreateController {

    private final GoodsCreateService goodsCreateService;

    @Operation(summary = "이미지를 포함한 상품을 업로드 하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 저장 성공, goodsId 반환"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 이미지", content = @Content(mediaType = "application/json", schema = @Schema(implementation = S3InvalidImageException.class)))
    })
    @PostMapping(value = "/new",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveGoods(@RequestPart GoodsSaveDto goodsSaveDto,
                                    @RequestPart(value = "images", required = false) List<MultipartFile> imageList,
                                    @RequestParam(name = "repImg", required = false) Integer repImg) {
        Long goodsId = goodsCreateService.saveGoods(goodsSaveDto, imageList, repImg);

        return new ResponseEntity(goodsId, CREATED);
    }

    @Operation(summary = "상품의 좌표를 저장하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 좌표 저장 성공, 좌표 Id 반환"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @PostMapping("/{goodsId}/coordinate/new")
    public ResponseEntity saveGoodsCoordinate(@RequestBody GoodsCoordinateDto goodsCoordinateDto,
                                              @PathVariable("goodsId") Long goodsId) {
        Long goodsCoordinateId = goodsCreateService.saveGoodsCoordinate(goodsId, goodsCoordinateDto);

        return new ResponseEntity(goodsCoordinateId, CREATED);
    }
}
