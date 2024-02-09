package site.jigume.global.aws.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.goods.service.GoodsService;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.MemberService;
import site.jigume.global.image.dto.ImageUploadRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class S3Controller {

    private final GoodsService goodsService;
    private final MemberService memberService;

    @Operation(summary = "상품 이미지 업데이트")
    @Parameters(value = {
            @Parameter(name = "goodsId", description = "이미지를 업로드 하려는 Goods의 PK", example = "1"),
            @Parameter(name = "repImg", description = "이미지가 대표 이미지인지", example = "true, false")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 저장 성공"),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/{goodsId}/image")
    public ResponseEntity updateImage(@RequestParam(value = "images", required = false) List<MultipartFile> multipartFiles,
                                      @PathVariable("goodsId") Long goodsId, @RequestParam(value = "repImg", required = false) Integer repImgYn) {
        goodsService.updateImage(multipartFiles, goodsId, repImgYn);

        return new ResponseEntity("이미지 저장 성공", OK);
    }
}
