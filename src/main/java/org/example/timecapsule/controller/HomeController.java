package org.example.timecapsule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
//@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String main() {
        return "main";
    }
}
