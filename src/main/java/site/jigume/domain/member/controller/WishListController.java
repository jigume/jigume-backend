package site.jigume.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.member.dto.wish.WishListDto;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.WishListService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "관심 상품을 등록하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 생성"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "404", description = "상품을 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoodsException.class)))
    })
    @PostMapping("/{goodsId}")
    public ResponseEntity saveWish(@PathVariable("goodsId") Long goodsId) {
        Long wishId = wishListService.save(goodsId);

        return new ResponseEntity(wishId, CREATED);
    }

    @Operation(summary = "관심 상품 리스트를 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "위시 리스트들을 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WishListDto.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @GetMapping("/list")
    public ResponseEntity getWishList() {
        List<WishListDto> wishList = wishListService.getWishList();

        return new ResponseEntity(wishList, OK);
    }

    @Operation(summary = "관심 상품 리스트 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 삭제"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @DeleteMapping("/{goodsId}")
    public ResponseEntity deleteWish(@PathVariable("goodsId") Long goodsId) {
        wishListService.delete(goodsId);

        return new ResponseEntity(OK);
    }
}
