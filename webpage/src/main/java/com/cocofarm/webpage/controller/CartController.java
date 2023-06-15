package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.service.CartService;
import com.google.gson.Gson;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @ResponseBody
    @PostMapping( value = "/selectCartList.and" , produces = "text/html;charset=utf-8")
    public String selectCartList(int member_no){
        ArrayList<CartDTO>list = cartService.selectCartList(member_no);
        System.out.println(list);
        return new Gson().toJson(list);

    }

    @PostMapping(value ="/insertcart.and", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public void insertCartAnd(CartDTO dto){
        cartService.insert(dto);
    }

}
