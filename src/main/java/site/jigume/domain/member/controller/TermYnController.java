package site.jigume.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.member.dto.term.TermYnDto;
import site.jigume.domain.member.service.TermYnService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/term/Yn")
public class TermYnController {

    private final TermYnService termYnService;

    @PostMapping
    public ResponseEntity saveTermYn(@RequestBody List<TermYnDto> termYnDtoList) {
        termYnService.save(termYnDtoList);

        return new ResponseEntity(OK);
    }
}
