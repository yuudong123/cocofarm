package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.common.ResultVO;
import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ChangeAndRefundDTO;
import com.cocofarm.webpage.domain.OrderProductDTO;
import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.CartService;
import com.cocofarm.webpage.service.OrderService;
import com.google.gson.Gson;

@Controller
public class AndOrderController {
    @Autowired
    OrderService orderservice;
    @Autowired
    CartService cartService;
    @Autowired
    BoardService boardService;

    @PostMapping(value = "orderinsert.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String OrderInsert(String vo) {
        System.out.println(vo);
        OrderVO ordervo = new Gson().fromJson(vo, OrderVO.class);

        int result = orderservice.OrderInsert(ordervo);
        String str = "";

        if (result > 1) {
            str = "성공";
            cartService.deleteCartProducts(ordervo.getOrderProductVOList());

        }
        ResultVO rVO = new ResultVO();
        rVO.setResult(str);
        rVO.setResultCode(result);
        return new Gson().toJson(rVO);
    }

    @PostMapping(value = "orderproductupdate.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void OrderProductStatusUpdate(String vo) {
        OrderProductVO tempVo = new Gson().fromJson(vo, OrderProductVO.class);
        orderservice.OrderProductStatusUpdate(tempVo);
    }

    @PostMapping(value = "changeandrefundinsert.and")
    @ResponseBody
    public void ChangeAndRefundInsert(String dto) {
        ChangeAndRefundDTO tempDto = new Gson().fromJson(dto, ChangeAndRefundDTO.class);
        orderservice.ChangeAndRefundInsert(tempDto);
    }

    // @PostMapping(value = "orderproductreviewwritepage.and", produces =
    // "text/html;charset=utf-8")
    // @ResponseBody
    // public String OrderProductReviewWritePage(int orderproduct_id) {
    // OrderProductVO vo =
    // orderservice.OrderProductReviewWritePage(orderproduct_id);
    // return new Gson().toJson(vo);
    // }

    @PostMapping(value = "orderproductreviewone.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectreviewboard(int orderproduct_id) {
        BoardVO vo = orderservice.selectreviewboard(orderproduct_id);
        return new Gson().toJson(vo);
    }

    // @PostMapping(value = "orderproductlowerlist.and", produces =
    // "text/html;charset=utf-8")
    // @ResponseBody
    // public String OrderProductLowerList(String order_id) {
    // OrderVO vo = new OrderVO();
    // vo.setOrder_id(order_id);
    // ArrayList<OrderProductVO> list = orderservice.OrderProductLowerList(vo);
    // System.out.println(list + "문제문제");
    // return new Gson().toJson(list);
    // }

    @PostMapping(value = "myorderlist.and")
    @ResponseBody
    public String MyOrderList(int member_no) {
        ArrayList<OrderProductDTO> list = orderservice.MyOrderList(member_no);
        return new Gson().toJson(list);
    }

}
