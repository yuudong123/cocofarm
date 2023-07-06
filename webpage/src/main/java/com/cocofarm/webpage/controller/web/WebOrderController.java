package com.cocofarm.webpage.controller.web;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.service.CartService;
import com.cocofarm.webpage.service.ImageService;
import com.cocofarm.webpage.service.OrderService;
import com.cocofarm.webpage.service.ProductService;

@Controller
@SessionAttributes("userinfo")
public class WebOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;
    @Autowired
    CartService cartService;

    @PostMapping(value = "order/page") // url
    public ModelAndView getSelectProduct(CartDTO dto, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        ProductVO vo = productService.selectProduct(dto.getProduct_id());
        ArrayList<ImageDTO> list = imageService.selectAllImageWithProductId(dto.getProduct_id());
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("vo", vo);
        mav.addObject("list", list);
        mav.addObject("member", member);
        mav.setViewName("product/orderpage");// 경로

        return mav;
    }

    @PostMapping("order/cartorderpage")
    public ModelAndView getSelectProduct2(@RequestParam(value = "cart", required = false) String[] order,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        List<String> list = Arrays.asList(order);
        ArrayList<CartDTO> cartList = cartService.selectCartList(list);
        int total = cartList.stream().mapToInt(cart -> cart.getProduct_price() * cart.getAmount()).sum();

        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("list", cartList);
        mav.addObject("member", member);
        mav.addObject("allTotalPrice", total);
        mav.setViewName("product/cartorderpage");// 경로
        return mav;
    }

    // 주문 결제 버튼 눌렀을때
    @PostMapping("order/payorderpage")
    public ModelAndView insertOrderPage(@RequestParam(value = "cart", required = false) String[] order,
            HttpSession session) {
        OrderVO vo = new OrderVO();
        ModelAndView mav = new ModelAndView();
        List<String> list = Arrays.asList(order);
        ArrayList<CartDTO> cartList = cartService.selectCartList(list);
        int total = cartList.stream().mapToInt(cart -> cart.getProduct_price() *
                cart.getAmount()).sum();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        vo.setMember_no(member.getMember_no());
        vo.setAddress(member.getAddress());
        vo.setPrice(total);
        vo.setOrderProductVOList(cartList);
        vo.setOrderdate(new Date());
        int ok = orderService.OrderInsert(vo);
        mav.addObject("ok", ok);
        mav.addObject("list", cartList);
        mav.addObject("member", member);
        mav.addObject("allTotalPrice", total);
        mav.setViewName("product/payresult");// 경로
        return mav;
    }

    // @PostMapping(value = "/product/orderpage")
    // public ModelAndView cartInsert2(@RequestBody CartDTO dto, HttpSession
    // session) {
    // ModelAndView mav = new ModelAndView();
    // System.out.println(dto);
    // MemberVO member = (MemberVO) session.getAttribute("userinfo");
    // int member_no = Integer.parseInt(member.getMember_no() + "");
    // ProductVO productvo = productService.selectProduct(dto.getProduct_id());
    // dto.setProduct_price(productvo.getPrice());
    // dto.setMember_no(member.getMember_no());
    // cartService.insert(dto);
    // ArrayList<CartDTO> list = cartService.selectCartList(member_no);
    // mav.addObject("list", list);
    // mav.setViewName("product/cart");
    // return mav;
    // }

}
