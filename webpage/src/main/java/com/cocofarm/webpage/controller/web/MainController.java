package com.cocofarm.webpage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ProductService;

@Controller
@SessionAttributes("userinfo")
public class MainController {

    @Autowired
    BoardService boardService;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ModelAndView mainpage() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("eventlist", boardService.selectList("event", new CriteriaDTO(1, 4)));
        mav.addObject("noticelist", boardService.selectList("notice", new CriteriaDTO(1, 4)));
        mav.addObject("productlist", productService.selectProductList(402));
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
