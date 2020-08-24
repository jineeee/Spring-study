package com.example.spring_study.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // /hello로 들어오면 자동으로 연결
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello"; // templates에서 hello라는 이름의 파일명을 찾아 리턴
    }

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam(name="name", required = false) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return name;
    }

    // api 방식
    @GetMapping("hello-api")
    @ResponseBody // http의 body에 문자 내용을 직접 반환
    public Hello helloAPI(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
