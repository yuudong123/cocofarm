package com.cocofarm.webpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    OrderService orderservice;

    @PostMapping(value = "orderinsert.and")
    @ResponseBody
    public void OrderInsert(OrderVO vo) {
        orderservice.OrderInsert(vo);
    }
}
