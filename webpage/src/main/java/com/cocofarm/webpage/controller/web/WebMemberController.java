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

    @GetMapping(value = "/login")
    public ModelAndView loginGET(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("prevPage", request.getHeader("referer"));
        mav.setViewName("member/login");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public String loginPOST(@RequestBody MemberVO emailAndPassword, HttpSession session) {
        MemberVO userinfo = memberService.login(emailAndPassword);
        if (userinfo != null) {
            session.setAttribute("userinfo", userinfo);
            return "true";
        } else {
            return "false";
        }
    }

}
