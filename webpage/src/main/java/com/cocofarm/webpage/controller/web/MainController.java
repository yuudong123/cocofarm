package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("userinfo")
public class MainController {

    @GetMapping("/")
    public ModelAndView mainpage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/termsofuse")
    public ModelAndView termsofuse() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("etc/termsofuse");
        return mav;
    }

    @GetMapping("/privacypolicy")
    public ModelAndView privacypolicy() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("etc/privacypolicy");
        return mav;
    }

    @GetMapping(value = "/admin/home")
    public ModelAndView adminHome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminhome");
        return mav;
    }
}
