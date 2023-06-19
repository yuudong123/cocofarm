package com.cocofarm.webpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.service.OrderService;

@Controller
public class OrderController {

    @Autowired 
    OrderService orderService;

    @PostMapping(value = "selectordermember.and", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void selectOrderMember(int member_no){
        orderService.selectOrderMember(member_no);
    }
    
}
