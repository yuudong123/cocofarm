package com.cocofarm.webpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.service.MemberService;
import com.google.gson.Gson;

@RestController
public class MemberController {

    @Autowired
    MemberService service;

    @RequestMapping(value = "login")
    public String login(String email, String password) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        vo.setPassword(password);

        return new Gson().toJson(service.login(vo));
    }
}
