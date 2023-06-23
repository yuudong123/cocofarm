package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/*")
public class AdminController {

    @GetMapping(value = "/product")
    public ModelAndView adminProduct() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("");
        return mav;
    }

    @GetMapping(value = "/order")
    public ModelAndView adminOrder() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("");
        return mav;
    }

    @GetMapping(value = "/board")
    public ModelAndView adminBoard() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/admin/main");
        return mav;
    }

    @GetMapping(value = "/member")
    public ModelAndView adminMember() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("");
        return mav;
    }
}
