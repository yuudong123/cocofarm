package com.cocofarm.webpage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping(value = "about/location")
    public String location() {
        return "about/location";
    }
}
