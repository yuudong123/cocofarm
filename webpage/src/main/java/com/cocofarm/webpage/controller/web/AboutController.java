package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({ "userinfo" })
@Controller
public class AboutController {

    @GetMapping(value = "about/location")
    public String location() {
        return "about/location";
    }

    @GetMapping(value = "about/overview")
    public String overview() {
        return "about/overview";
    }
}
