package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.service.CartService;
import com.cocofarm.webpage.service.ImageService;
import com.cocofarm.webpage.service.ProductService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userinfo")
public class WebCartController {

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;

    // @ResponseBody
    // @PostMapping(value = "/selectcartlist")
    // public ArrayList<CartDTO> selectCartList(int member_no) {
    // ArrayList<CartDTO> list = cartService.selectCartList(member_no);
    // return list;
    // }

    // @PostMapping(value = "cartinsert2")
    // public ModelAndView cartInsert2(@RequestBody CartDTO dto, HttpSession
    // session) {
    // ModelAndView mav = new ModelAndView();
    // System.out.println(dto);
    // MemberVO member = (MemberVO) session.getAttribute("userinfo");
    // // System.out.println(member + "멤버 나오나??");
    // int member_no = Integer.parseInt(member.getMember_no() + "");
    // // System.out.println(member_no + "멤버 no는 나오고 있나?");
    // // System.out.println(list + "리스트는 얼마나 나오는지 궁금");
    // ProductVO productvo = productService.selectProduct(dto.getProduct_id());
    // dto.setProduct_price(productvo.getPrice());
    // dto.setMember_no(member.getMember_no());
    // cartService.insert(dto);
    // ArrayList<CartDTO> list = cartService.selectCartList(member_no);
    // // 장바구니에 넣음과 동시에 장바구니 리스트를 보여줄려고.
    // mav.addObject("list", list);
    // mav.setViewName("product/cart");
    // return mav;
    // }

    @ResponseBody
    @PostMapping(value = "cartinsert")
    public String cartInsert(@RequestBody CartDTO dto, HttpSession session) {
        System.out.println(dto);
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        ProductVO productvo = productService.selectProduct(dto.getProduct_id());
        dto.setProduct_price(productvo.getPrice());
        dto.setMember_no(member.getMember_no());
        cartService.insert(dto);
        ArrayList<CartDTO> list = cartService.selectCartList(member.getMember_no());
        // 장바구니에 넣음과 동시에 장바구니 리스트를 보여줄려고.
        return "{\"status\":\"OK\",\"carts\":" + new Gson().toJson(list) + "}";
    }

    @GetMapping(value = "/cart")
    public ModelAndView cartPage(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        ArrayList<CartDTO> list = cartService.selectCartList(member.getMember_no());
        mav.addObject("list", list);
        mav.setViewName("product/cart");
        return mav;
    }

    // 장바구니 상품 한개 삭제
    @PostMapping(value = "/cart/delete")
    @ResponseBody
    public int deleteCartProductOne(@RequestBody CartDTO dto, HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        dto.setMember_no(member.getMember_no());
        return cartService.deleteCartProductOne(dto);
    }

    // Ajax로 받아서 처리 할려ㅑ고 만들어놓음.
    // html을 그대로보낼거임 그래서 리턴값이 String
    @GetMapping(value = "/cart2")
    @ResponseBody
    public String cartPage2(HttpSession session) {
        // ModelAndView mav = new ModelAndView();
        // MemberVO member = (MemberVO) session.getAttribute("userinfo");
        // ArrayList<CartDTO> list = cartService.selectCartList(member.getMember_no());
        // mav.addObject("list", list);
        // mav.setViewName("product/cart");
        return "ddsda";
    }

    // 장바구니 전체 삭제
    @PostMapping(value = "/cart/alldelete")
    @ResponseBody
    public int deleteCartList(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        int member_no = member.getMember_no();
        return cartService.deleteCartList(member_no);
    }

}
