package com.jigume.controller;

import com.jigume.dto.member.TokenDto;
import com.jigume.entity.member.LoginProvider;
import com.jigume.exception.member.LoginMemberException;
import com.jigume.service.member.MemberService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "404", description = "로그인 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginMemberException.class))),
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("login-provider") String provider,
                                @RequestParam("authorization-code") String code) {

        TokenDto login = memberService.login(LoginProvider.toLoginProvider(provider), code);

        return new ResponseEntity(login, OK);
    }

    @PostMapping("/member/token")
    public ResponseEntity refreshNewToken(String refreshToken) {
        TokenDto tokenDto = memberService.reissueToken(refreshToken);

        return new ResponseEntity(tokenDto, OK);
    }
}
