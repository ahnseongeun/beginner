package com.inflearnbeginner.beginner.controller;

import com.inflearnbeginner.beginner.domain.Member;
import com.inflearnbeginner.beginner.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;

        //AOP가 적용이 되면 실제 Service의 복제가 찍히는 것을 알수 있다.
        //joinPoint.proceed()가 호출 되면 그때 진짜 Service가 호출된다.
        System.out.println("memberService = " + memberService.getClass());
    }

    @GetMapping("/members/new") //회원가입
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members= memberService.findMembers();
        model.addAttribute("memberTest",members);
        return  "members/memberList";
    }

}
