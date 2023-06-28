package com.cocofarm.webpage.controller.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @GetMapping(value = "/member/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession().setAttribute("userinfo", null);
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo", null);
        mav.setViewName("/index");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/member/pwconfirm")
    public String pwconfirm(@RequestBody HashMap<String,String> param, HttpSession session) {
        
        MemberVO vo = (MemberVO) session.getAttribute("userinfo");
        if (param.get("password").equals(vo.getPassword().toString())) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value="/member/join")
    public String join(MemberVO vo) {
        return "member/join";
    }

    @ResponseBody
    @PostMapping(value = "/member/createMember")
    public int createMember(@RequestBody HashMap<String, String> param) {
        MemberVO vo = new MemberVO();
        vo.setEmail(param.get("email"));
        vo.setPassword(param.get("password"));
        vo.setNickname(param.get("nickname"));
        vo.setPhonenumber(param.get("phonenumber"));
        vo.setAddress(param.get("address"));
        vo.setSns(param.get("sns"));

        return memberService.join(vo);
    }

    @GetMapping(value="/member/test")
    public String test() {
        return "member/test";
    }

    @GetMapping(value="/member/myinfo")
    public String myinfo() {
        return "member/myinfo";
    }

    @GetMapping(value="/member/myboard")
    public String myboard() {
        return "member/myboard";
    }

    @GetMapping(value="/member/mydevice")
    public String mydevice() {
        return "member/mydevice";
    }

    @GetMapping(value="/member/cscenter")
    public String cscenter() {
        return "member/cscenter";
    }

    @GetMapping(value="/member/away")
    public String away() {
        return "member/away";
    }


    @ResponseBody
    @PostMapping(value = "/member/email_search")
    public String email_search(@RequestBody MemberVO vo) {
        System.out.println(vo.getEmail());
        return memberService.email_search(vo.getEmail());
    }
}
