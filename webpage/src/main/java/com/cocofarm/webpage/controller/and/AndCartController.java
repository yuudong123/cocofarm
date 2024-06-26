package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.service.CartService;
import com.google.gson.Gson;

@RestController
public class AndCartController {
    @Autowired
    CartService cartService;

    @PostMapping(value = "/selectCartList.and", produces = "text/html;charset=utf-8")
    public String selectCartList(int member_no) {
        ArrayList<CartDTO> list = cartService.selectCartList(member_no);
        System.out.println(list);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/insertcart.and", produces = "text/html; charset=UTF-8")
    public void insertCartAnd(CartDTO dto) {
        cartService.insert(dto);
    }

    // 장바구니 상품 한개 삭제
    @PostMapping(value = "/deletecartone.and", produces = "text/html; charset=UTF-8")
    public void deleteCartProductOne(CartDTO dto) {
        cartService.deleteCartProductOne(dto);
    }

    // 장바구니 전체 삭제
    @PostMapping(value = "/deletecartlist.and", produces = "text/html; charset=UTF-8")
    public void deleteCartList(int member_no) {
        cartService.deleteCartList(member_no);
    }

}
