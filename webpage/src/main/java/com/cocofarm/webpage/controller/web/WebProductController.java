package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.service.ImageService;
import com.cocofarm.webpage.service.ProductService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userinfo")

public class WebProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;

    @GetMapping("/product")
    public ModelAndView selectBoardList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("product/productpage");
        // Model에 데이터 추가
        return mav;
    }

    @PostMapping("/productlist")
    @ResponseBody
    public String getProductList(@RequestBody HashMap<String, Integer> param) {
        ArrayList<ProductVO> list = productService.selectProductList(param.get("category"));
        return new Gson().toJson(list);
    }

    @GetMapping("/productdetail")
    public ModelAndView getSelectProduct(int product_id, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        ProductVO vo = productService.selectProduct(product_id);
        ArrayList<ImageDTO> list = imageService.selectAllImageWithProductId(product_id);
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("vo", vo);
        mav.addObject("list", list);
        mav.addObject("member", member);
        mav.setViewName("product/productdetail");
        return mav;
    }

    /**
     * public ArrayList<ProductVO> selectProductListWithImage() {
     * ArrayList<ProductVO> list = productMapper.selectProductListWithImage();
     * return list;
     * }
     */

}
