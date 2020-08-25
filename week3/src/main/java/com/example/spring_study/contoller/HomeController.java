package com.example.spring_study.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 최초 접속 시 home 뷰 리턴
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
