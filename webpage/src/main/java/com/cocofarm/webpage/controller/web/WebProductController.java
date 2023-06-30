package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebProductController {

    @GetMapping("/product/productpage")
    public String product(Model model) {
        model.addAttribute("data1", "데이터1");
        return "/product/productpage.html";
    }

}
