package site.jigume.global.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CheckController {

    @GetMapping("/check")
    public ResponseEntity checkServer() {

        return new ResponseEntity("서버 작동 중", OK);
    }
}
