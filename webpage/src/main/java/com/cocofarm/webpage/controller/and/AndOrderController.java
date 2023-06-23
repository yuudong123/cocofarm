package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.common.ResultVO;
import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.service.OrderService;
import com.google.gson.Gson;

@Controller
public class AndOrderController {
    @Autowired
    OrderService orderservice;

    @PostMapping(value = "orderinsert.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String OrderInsert(String vo) {
        System.out.println(vo);
        OrderVO ordervo = new Gson().fromJson(vo, OrderVO.class);

        int result = orderservice.OrderInsert(ordervo);
        String str = "";
        if (result > 1) {
            str = "성공";
        }
        ResultVO rVO = new ResultVO();
        rVO.setResult(str);
        rVO.setResultCode(result);
        return new Gson().toJson(rVO);
    }

    @PostMapping(value = "orderproductlist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String OrderProductList(int member_no) {
        OrderVO vo = new OrderVO();
        vo.setMember_no(member_no);
        ArrayList<OrderProductVO> list = orderservice.OrderProductList(vo);

        return new Gson().toJson(list);
    }

    @PostMapping(value = "orderproductupdate.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void OrderProductStatusUpdate(String vo) {
        OrderProductVO tempVo = new Gson().fromJson(vo, OrderProductVO.class);
        orderservice.OrderProductStatusUpdate(tempVo);
    }

}
