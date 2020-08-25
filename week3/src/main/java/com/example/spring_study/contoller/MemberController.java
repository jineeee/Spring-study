package com.example.spring_study.contoller;

import com.example.spring_study.domain.Member;
import com.example.spring_study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService service;

    // 컨트롤러 생성 시 싱글톤 서비스 객체를 가져와 주입
    @Autowired
    public MemberController(MemberService service){
        this.service = service;
    }

    // GET 방식으로 /members/new url로 접속 시 createMemberForm 뷰 리턴
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    // POST 방식으로 /members/new url로 접속 시 실행
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        System.out.println(form.getName());
        service.join(member);
        return "redirect:/";
    }

    // GET 방식으로 /members url로 접속 시 memberList 뷰 리턴
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = service.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
