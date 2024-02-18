package site.jigume.domain.admin.controller;

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
import site.jigume.domain.admin.dto.TermDto;
import site.jigume.domain.admin.service.TermService;
import site.jigume.domain.member.exception.auth.AuthException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/term")
public class TermController {

    private final TermService termService;

    @Operation(summary = "약관 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "약관이 성공적으로 되었습니다."),
            @ApiResponse(responseCode = "400", description = "인가된 사용자가 아닙니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthException.class)))
    })
    @PostMapping
    public ResponseEntity saveTerm(@RequestBody TermDto termDto) {
        Long id = termService.save(termDto);

        return new ResponseEntity(id, CREATED);
    }
}
