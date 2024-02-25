package site.jigume.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.admin.exception.TermException;
import site.jigume.domain.member.dto.term.TermYnDto;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.service.TermYnService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/term/Yn")
public class TermYnController {

    private final TermYnService termYnService;

    @Operation(summary = "약관 동의 상태를 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장성공"),
            @ApiResponse(responseCode = "400", description = "약관은 중복 될 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TermException.class))),
            @ApiResponse(responseCode = "400", description = "필수 약관은 반드시 동의해야 합니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TermException.class))),
            @ApiResponse(responseCode = "404", description = "약관을 찾을 수 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TermException.class))),
            @ApiResponse(responseCode = "404", description = "토큰이 유효하지 않거나, 토큰의 멤버를 조회할 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping
    public ResponseEntity saveTermYn(@RequestBody List<TermYnDto> termYnDtoList) {
        termYnService.save(termYnDtoList);

        return new ResponseEntity(CREATED);
    }
}
