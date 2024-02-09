package site.jigume.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jigume.domain.admin.dto.TermDto;
import site.jigume.domain.admin.service.TermService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/term")
public class TermController {

    private final TermService termService;

    @PostMapping
    public ResponseEntity saveTerm(@RequestBody TermDto termDto) {
        Long id = termService.save(termDto);

        return new ResponseEntity(id, CREATED);
    }
}
