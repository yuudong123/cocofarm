package com.cocofarm.webpage.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.service.MemberService;

@Controller
@SessionAttributes({ "userinfo", "prevpage" })
public class WebMemberController {

    @Autowired
    MemberService memberService;

    @GetMapping(value = "/member/login")
    public ModelAndView loginGET(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("prevPage", request.getHeader("referer"));
        mav.setViewName("member/login");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/member/login")
    public String loginPOST(@RequestBody MemberVO emailAndPassword, HttpSession session) {
        MemberVO userinfo = memberService.login(emailAndPassword);
        if (userinfo != null) {
            session.setAttribute("userinfo", userinfo);
            return "true";
        } else {
            return "false";
        }
    }

     @GetMapping(value = "/member/connect")
     public String memberConnect() {
            return "member/connect";
    }

    @GetMapping(value="/member/join")
    public String join() {
        return "member/join";
    }

        @GetMapping(value="/member/test")
    public String test() {
        return "member/test";
    }

    @ResponseBody
    @PostMapping(value = "/member/email_search")
    public String email_search(@RequestBody MemberVO vo) {
        System.out.println(vo.getEmail());
        return memberService.email_search(vo.getEmail());
    }
}
