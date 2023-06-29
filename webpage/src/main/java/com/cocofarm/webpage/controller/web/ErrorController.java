package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("err/*")
@SessionAttributes("userinfo")
public class ErrorController {

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("etc/access-denied");
        return mav;
    }

    @GetMapping("/login-required")
    public ModelAndView loginRequired() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("etc/login-required");
        return mav;
    }
}
