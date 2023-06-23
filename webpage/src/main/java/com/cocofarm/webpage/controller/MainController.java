package com.cocofarm.webpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
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

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("etc/access-denied");
        return mav;
    }

}
