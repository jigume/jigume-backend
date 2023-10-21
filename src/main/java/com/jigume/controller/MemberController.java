package com.jigume.controller;

import com.jigume.dto.member.LoginResponseDto;
import com.jigume.dto.member.MemberInfoDto;
import com.jigume.dto.member.TokenDto;
import com.jigume.entity.goods.ImageUploadRequest;
import com.jigume.entity.member.LoginProvider;
import com.jigume.exception.auth.exception.AuthMemberNotFoundException;
import com.jigume.exception.auth.exception.InvalidAuthorizationCodeException;
import com.jigume.exception.global.exception.GlobalServerErrorException;
import com.jigume.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인 및 회원 가입 - 로그인 시 반환되는 권한(GUEST, USER)에 따라 신규 유저 구별")
    @Parameters(value = {
            @Parameter(name = "login-provider", description = "소셜 로그인 종류", example = "naver"),
            @Parameter(name = "authorization-code", description = "리다이렉트로 받은 허가 코드", example = "231313fsjdfsfs")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 Authorization code 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidAuthorizationCodeException.class))),
            @ApiResponse(responseCode = "500", description = "소셜 로그인 서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalServerErrorException.class)))
    })
    @PostMapping("/member/login")
    public ResponseEntity login(@RequestParam("login-provider") String provider,
                                @RequestParam("authorization-code") String code) {

        LoginResponseDto login = memberService.login(LoginProvider.toLoginProvider(provider), code);

        return new ResponseEntity(login, OK);
    }

    @Operation(summary = "refresh 토큰을 이용해 액세스 토큰 재발급")
    @Parameters(value = {
            @Parameter(name = "refreshToken", description = "리프레쉬 토큰", example = "ey231313fsjdfsfs..")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 및 리프레쉬 토큰 갱신", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리프레쉬 토큰을 가진 회원이 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/member/token")
    public ResponseEntity refreshNewToken(@RequestBody String refreshToken) {
        TokenDto tokenDto = memberService.reissueToken(refreshToken);

        return new ResponseEntity(tokenDto, OK);
    }

    @Operation(summary = "Guest 유저에게 주소, 닉네임 받아 업로드하고 User로 바꾼다.")
    @Parameters(value = {
            @Parameter(name = "memberInfoDto", description = "멤버 정보", schema = @Schema(implementation = MemberInfoDto.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 업데이트 했음"),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/member/new")
    public ResponseEntity updateMemberInfo(@RequestBody MemberInfoDto memberInfoDto) {
        memberService.updateMemberInfo(memberInfoDto);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "Guest 유저에게 프로필 이미지 받아 S3에 업로드하고 User로 바꾼다.")
    @Parameters(value = {
            @Parameter(name = "이미지", description = "멤버 정보", schema = @Schema(implementation = ImageUploadRequest.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 업데이트 했음"),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/member/profile/new")
    public ResponseEntity updateMemberProfileImage(ImageUploadRequest imageUploadRequest) {
        memberService.updateMemberProfileImage(imageUploadRequest);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "User의 닉네임 같은 정보를 가져온다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 파일 업데이트 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberInfoDto.class))),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthMemberNotFoundException.class)))
    })
    @PostMapping("/member/profile")
    public ResponseEntity getMemberInfo() {
        MemberInfoDto memberInfo = memberService.getMemberInfo();

        return new ResponseEntity<>(memberInfo, OK);
    }
}
