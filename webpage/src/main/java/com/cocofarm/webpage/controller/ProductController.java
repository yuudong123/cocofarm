package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.service.ProductService;
import com.google.gson.Gson;


@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    /*
     * @GetMapping(value = {"/product/plant", "/product/device"})
     * public ArrayList<ProductVO> getProductList(int product_id){
     * ArrayList<ProductVO> products =
     * productService.selectProductPlant(product_id);
     * return products;
     * }
     */

    @PostMapping(value = "/selectProductList.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductList(int category_cd) {
        ArrayList<ProductVO> productList = productService.selectProductList(category_cd);
        return new Gson().toJson(productList);
    }

    @GetMapping(value = "/selectProductContent.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductConetent(int product_id) {
        ProductVO productContent = productService.selectProductContent(product_id);
        return new Gson().toJson(productContent);
    }

}

/*
 * @Autowired
 * BoardService boardService;
 * 
 * @Autowired
 * ReplyService replyService;
 * 
 * @GetMapping(value = { "/board/notice", "/board/qna", "/board/event" })
 * public ModelAndView selectBoardList(int code) {
 * ModelAndView mav = new ModelAndView();
 * ArrayList<BoardVO> boardlist = boardService.selectList(code);
 * mav.addObject("boardlist", boardlist);
 * mav.setViewName("/");
 * return mav;
 * }
 */