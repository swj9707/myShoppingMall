package com.example.myshoppingmall.controller;

import com.example.myshoppingmall.dto.MemberDto;
import com.example.myshoppingmall.entity.Member;
import com.example.myshoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/registNew")
    public String memberForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/memberForm";
    }

    @PostMapping(value="/registNew")
    public String memberForm(@Valid MemberDto memberDto,
                             BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try{
            Member member = Member.createMember(memberDto, passwordEncoder, false);
            memberService.saveMember(member);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value="/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value="/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
