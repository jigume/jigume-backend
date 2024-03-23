package site.jigume.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.jigume.domain.member.dto.LoginResponseDto;
import site.jigume.domain.member.dto.MemberInfoDto;
import site.jigume.domain.member.dto.TokenDto;
import site.jigume.domain.member.dto.UpdateMemberInfoDto;
import site.jigume.domain.member.entity.LoginProvider;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.member.MemberException;
import site.jigume.domain.member.service.MemberService;
import site.jigume.global.exception.GlobalServerErrorException;
import site.jigume.global.image.dto.ImageUploadRequest;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인 및 회원 가입 - 로그인 시 반환되는 권한(GUEST, USER)에 따라 신규 유저 구별")
    @Parameters(value = {
            @Parameter(name = "login-provider", description = "소셜 로그인 종류", example = "naver"),
            @Parameter(name = "authorization-code", description = "리다이렉트로 받은 허가 코드", example = "231313fsjdfsfs")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 Authorization code 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class))),
            @ApiResponse(responseCode = "500", description = "소셜 로그인 서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalServerErrorException.class)))
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("login-provider") String provider,
                                @RequestParam("authorization-code") String code,
                                HttpServletRequest request) {
        String domain = request.getHeader("Origin");
        LoginResponseDto login = memberService.login(LoginProvider.toLoginProvider(provider), code, domain);

        return new ResponseEntity(login, OK);
    }

    @Operation(summary = "refresh 토큰을 이용해 액세스 토큰 재발급")
    @Parameters(value = {
            @Parameter(name = "refreshToken", description = "리프레쉬 토큰", example = "ey231313fsjdfsfs..")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 및 리프레쉬 토큰 갱신", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 리프레쉬 토큰을 가진 회원이 존재하지 않음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/token")
    public ResponseEntity refreshNewToken(@RequestBody String refreshToken) {
        TokenDto tokenDto = memberService.reissueToken(refreshToken);

        return new ResponseEntity(tokenDto, OK);
    }

    @Operation(summary = "Guest 유저에게 주소, 닉네임 받아 업로드하고 User로 바꾼다.")
    @Parameters(value = {
            @Parameter(name = "memberInfoDto", description = "멤버 정보", schema = @Schema(implementation = UpdateMemberInfoDto.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 업데이트 했음"),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/info")
    public ResponseEntity updateMemberInfo(@Valid @RequestBody UpdateMemberInfoDto updateMemberInfoDto) {
        memberService.updateMemberInfo(updateMemberInfoDto);

        return new ResponseEntity(OK);
    }

    @Operation(summary = "Guest 유저에게 프로필 이미지 받아 S3에 업로드하고 User로 바꾼다.")
    @Parameters(value = {
            @Parameter(name = "이미지", description = "멤버 정보", schema = @Schema(implementation = ImageUploadRequest.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 업데이트 했음"),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping("/profile")
    public ResponseEntity updateMemberProfileImage(@RequestPart MultipartFile multipartFile) {
        Long fileId = memberService.saveMemberImage(multipartFile);

        return new ResponseEntity(fileId, OK);
    }

    @Operation(summary = "User의 닉네임 같은 정보를 가져온다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 정보를 가져온다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberInfoDto.class))),
            @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @GetMapping("/profile")
    public ResponseEntity getMemberInfo() {
        MemberInfoDto memberInfo = memberService.getMemberInfo();

        return new ResponseEntity<>(memberInfo, OK);
    }

    @Operation(summary = "닉네임 중복 검사를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상적인 닉네임"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 닉네임", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberException.class))),
            @ApiResponse(responseCode = "400", description = "중복된 닉네임", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberException.class)))
    })
    @GetMapping("/nickname")
    public ResponseEntity checkDuplicateNickname(@RequestParam String nickname) {
        memberService.checkDuplicateNickname(nickname);

        return new ResponseEntity<>(OK);
    }
}
