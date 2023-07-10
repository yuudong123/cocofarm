package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.common.CodeTable;
import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.OrderDTO;
import com.cocofarm.webpage.domain.OrderProductDTO;
import com.cocofarm.webpage.domain.OrderProductVO;
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

    // 주문 결제 버튼 눌렀을때 14:38 주석 다른걸로 테스트해봄.
    // @PostMapping("order/payresult")
    // public ModelAndView insertOrderPage(@RequestParam(value = "cart", required =
    // false) String[] order,
    // HttpSession session) {
    // OrderVO vo = new OrderVO();
    // ModelAndView mav = new ModelAndView();
    // ArrayList<CartDTO> cartList = null ;
    // int total= 0;
    // if(order != null){
    // List<String> list = Arrays.asList(order);
    // cartList= cartService.selectCartList(list);
    // }else{
    // //cartList = // cartService.
    // //cartList= cartService.selectCartList(list);
    // }
    // total = cartList.stream().mapToInt(cart -> cart.getProduct_price() *
    // cart.getAmount()).sum();
    // MemberVO member = (MemberVO) session.getAttribute("userinfo");
    // vo.setMember_no(member.getMember_no());
    // vo.setAddress(member.getAddress());

    // vo.setPrice(total);
    // vo.setOrderProductVOList(cartList);
    // vo.setOrderdate(new Date());
    // OrderProductVO orderProductVO = new OrderProductVO();

    // int ok = orderService.OrderInsert(vo);
    // if (order.length == (ok - 1)) {
    // vo.setOrder_status_cd(CodeTable.ORDER_STATUS_ONREADY);
    // } else {
    // System.out.println("주문 실패");
    // }
    // orderProductVO.setOrder_status_cd(vo.getOrder_status_cd());
    // orderProductVO.setValue("배송준비");

    // mav.addObject("orderproductvo", orderProductVO);
    // mav.addObject("vo", vo);
    // mav.addObject("ok", ok);
    // mav.addObject("list", cartList);
    // mav.addObject("member", member);
    // mav.addObject("allTotalPrice", total);
    // mav.setViewName("product/orderpayresult");// 경로
    // return mav;
    // }

    public void insertOrderPage(HttpSession session, String[] order) {
        OrderVO vo = new OrderVO();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        vo.setMember_no(member.getMember_no());
        vo.setAddress(member.getAddress());
        List<String> list = Arrays.asList(order);
        ArrayList<CartDTO> cartList = cartService.selectCartList(list);
        int total = cartList.stream().mapToInt(cart -> cart.getProduct_price() *
                cart.getAmount()).sum();
        vo.setPrice(total);
        vo.setOrderProductVOList(cartList);
        vo.setOrderdate(new Date());
        int result = orderService.OrderInsert(vo);
        System.out.println(result + "결과");
    }

    // 결제 상세 내역
    @RequestMapping("order/payresult")
    public ModelAndView insertOrderPage(@RequestParam(value = "order_id", required = false) String order_id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // if (order != null) {
        // insertOrderPage(session, order);
        // }
        System.out.println(order_id + "오더 아이디 찍힘");
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        ArrayList<OrderProductDTO> list = orderService.MyOrderDetail(order_id,
                member.getMember_no());
        mav.addObject("list", list);
        mav.setViewName("product/orderpayresult");// 경로
        return mav;
    }

    @RequestMapping("order/orderlist")
    public ModelAndView selectOrderList(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        ArrayList<OrderDTO> list = orderService.MyOrder(member.getMember_no());
        mav.addObject("list", list);
        mav.setViewName("product/orderlist");// 경로
        return mav;
    }

    /*
     * public ArrayList<OrderProductDTO> MyOrderList(int member_no) {
     * return ordermapper.MyOrderList(member_no);
     * 
     * }
     */

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
