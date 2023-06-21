package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.service.OrderService;
import com.google.gson.Gson;

@Controller
public class OrderController {
    @Autowired
    OrderService orderservice;

    @PostMapping(value = "orderinsert.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void OrderInsert(String vo) {
        System.out.println(vo);
        OrderVO ordervo = new Gson().fromJson(vo, OrderVO.class);

        orderservice.OrderInsert(ordervo);

    }
}
