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
}
