package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.common.CodeTable;
import com.cocofarm.webpage.domain.BoardQnAProductDTO;
import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.domain.ChangeAndRefundDTO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.OrderDTO;
import com.cocofarm.webpage.domain.OrderProductDTO;
import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.BoardService;
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
    @Autowired
    BoardService boardService;

    // 제품페이지 3가지 제품상세
    @ResponseBody
    @RequestMapping("product/productdetailcontent")
    public ModelAndView productdetailcontent(@RequestBody CartDTO dto,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        System.out.println(dto);
        ProductVO productvo = productService.selectProduct(dto.getProduct_id());
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("member", member);
        mav.addObject("productvo", productvo);
        mav.setViewName("product/productdetailcontent");// 경로
        return mav;
    }

    // 리뷰
    @ResponseBody
    @RequestMapping("product/reviewcontent")
    public ModelAndView reviewcontent(@RequestBody CartDTO dto,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        CriteriaDTO cri = new CriteriaDTO(203, null);
        cri.setProductId(dto.getProduct_id());
        ArrayList<QnaDTO> boardList = boardService.selectReviewListByProduct(cri);
        // System.out.println(boardList);
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("member", member);
        mav.addObject("boardlist", boardList);
        mav.setViewName("product/reviewcontent");// 경로
        return mav;
    }

    // Q&A
    @ResponseBody
    @RequestMapping("product/qnacontent")
    public ModelAndView qnacontent(@RequestBody CartDTO dto,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        CriteriaDTO cri = new CriteriaDTO(201, null);
        cri.setProductId(dto.getProduct_id());
        ArrayList<BoardQnAProductDTO> boardList = boardService.selectQnaListByProduct(cri);

        System.out.println(boardList);
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("boardlist", boardList);
        mav.addObject("member", member);
        mav.setViewName("product/qnacontent");// 경로
        return mav;
    }

    // 제품 바로 구매
    @PostMapping(value = "order/orderbuypage") // url
    public ModelAndView buyOneProductPage(CartDTO dto, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        ProductVO vo = productService.selectProductWithImage(dto.getProduct_id());
        ArrayList<CartDTO> cartList = new ArrayList<>();
        OrderVO ordervo = new OrderVO();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        ordervo.setMember_no(member.getMember_no());
        ordervo.setAddress(member.getAddress());
        System.out.println(dto + "디티오에 뭐가있을까");
        int total = dto.getAmount() * vo.getPrice();

        dto.setMember_no(member.getMember_no());
        dto.setProduct_price(vo.getPrice());
        cartList.add(dto);
        ordervo.setPrice(total);
        ordervo.setOrderProductVOList(cartList);
        ordervo.setOrderdate(new Date());
        orderService.OrderInsert(ordervo);

        mav.addObject("member", member);
        mav.setViewName("product/orderbuypage");
        return mav;
    }

    @PostMapping(value = "order/page") // url
    public ModelAndView getSelectProduct(CartDTO dto, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        ProductVO vo = productService.selectProductWithImage(dto.getProduct_id());

        OrderProductVO orderproductvo = new OrderProductVO();
        orderproductvo.setAmount(dto.getAmount());
        orderproductvo.setProduct_id(dto.getProduct_id());
        ArrayList<ImageDTO> list = imageService.selectAllImageWithProductId(dto.getProduct_id());
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("orderproductvo", orderproductvo);
        mav.addObject("vo", vo);
        mav.addObject("list", list);
        mav.addObject("member", member);
        mav.setViewName("product/orderpage");// 경로
        return mav;
    }

    @RequestMapping("order/cartorderpage")
    public ModelAndView getSelectProduct2(@RequestParam(value = "cart", required = false) String[] order,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        List<String> list = Arrays.asList(order);
        ArrayList<CartDTO> cartList = cartService.selectCartList(list);
        int total = cartList.stream().mapToInt(cart -> cart.getProduct_price() * cart.getAmount()).sum();
        System.out.println(cartList);

        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        mav.addObject("list", cartList);

        mav.addObject("member", member);
        mav.addObject("allTotalPrice", total);
        mav.setViewName("product/cartorderpage");// 경로
        return mav;
    }

    // 주문 결제 버튼 눌렀을때 14:38 주석 다른걸로 테스트해봄.
    @PostMapping("order/payresult")
    public ModelAndView insertOrderPage(@RequestParam(value = "cart", required = false) String[] order,
            HttpSession session) {
        OrderVO vo = new OrderVO();
        ModelAndView mav = new ModelAndView();
        ArrayList<CartDTO> cartList = new ArrayList<>();
        int total = 0;
        if (order != null) {
            List<String> list = Arrays.asList(order);
            cartList = cartService.selectCartList(list);
        } else {
            // cartList = // cartService.
            // cartList= cartService.selectCartList(list);
        }
        total = cartList.stream().mapToInt(cart -> cart.getProduct_price() *
                cart.getAmount()).sum();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        vo.setMember_no(member.getMember_no());
        vo.setAddress(member.getAddress());

        vo.setPrice(total);
        vo.setOrderProductVOList(cartList);
        vo.setOrderdate(new Date());
        OrderProductVO orderProductVO = new OrderProductVO();

        int ok = orderService.OrderInsert(vo);
        if (order != null && order.length == (ok - 1)) {
            vo.setOrder_status_cd(CodeTable.ORDER_STATUS_ONREADY);
        } else {
            System.out.println("주문 실패");
        }
        orderProductVO.setOrder_status_cd(vo.getOrder_status_cd());
        orderProductVO.setValue("배송준비");

        // 주문되면 장바구니에 있던거 삭제.
        cartService.deleteCartProducts(vo.getOrderProductVOList());

        mav.addObject("orderproductvo", orderProductVO);
        mav.addObject("vo", vo);
        mav.addObject("ok", ok);
        mav.addObject("list", cartList);
        mav.addObject("member", member);
        mav.addObject("allTotalPrice", total);
        mav.setViewName("product/orderpayresult1");// 경로
        return mav;
    }

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
    @RequestMapping("order/orderdetail")
    public ModelAndView insertOrderPage(@RequestParam(value = "order_id", required = false) String order_id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // if (order != null) {
        // insertOrderPage(session, order);
        // }

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

    // status_cd 바꾸는처리
    @ResponseBody
    @RequestMapping("order/update")
    public void updateStatusCode(@RequestBody OrderProductVO dto, HttpSession session) {
        // System.out.println(dto);
        System.out.println(dto.getOrderproduct_id());
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        dto.setMember_no(member.getMember_no());

        orderService.OrderProductStatusUpdate(dto);
        System.out.println(dto);

    }

    // 배송조회용 controller
    @ResponseBody
    @RequestMapping("order/deliberystatus")
    public ModelAndView deliberyStatus(@RequestBody OrderVO v, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // System.out.println(v.getOrder_id());
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        // System.out.println(member.getMember_no());
        OrderVO vo = orderService.selectorder(v.getOrder_id(), member.getMember_no());
        mav.addObject("ordervo", vo);
        mav.setViewName("product/orderdeliberypage");
        return mav;
    }

    // 리뷰쓰기용 controller
    @ResponseBody
    @RequestMapping("order/reviewwritepage")
    public ModelAndView reviewWrite(@RequestBody OrderProductVO v, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        // System.out.println(member.getMember_no());
        OrderProductVO vo = orderService.selectorderproduct(v.getOrderproduct_id(), member.getMember_no());
        // System.out.println(v.getOrder_id());
        // System.out.println(v);
        mav.addObject("orderproductvo", vo);
        mav.setViewName("product/reviewwritepage");
        return mav;
    }

    // 리뷰저장용 controller
    @ResponseBody
    @RequestMapping("order/reviewsave")
    public ModelAndView reviewSave(@RequestBody BoardVO v, OrderProductVO vo, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        OrderProductVO orderproductvo = orderService.selectorderproduct(v.getOrderproduct_id(),
                member.getMember_no());
        // System.out.println(vo);
        v.setMember_no(member.getMember_no());
        v.setBoard_category_cd(203);
        v.setMainimage(v.getMainimage());
        // 메인이미지 자바스크립트쪽에서 filename으로 보내서 메인 이미지로 넣어줌 문제될 시 삭제.
        v.setNickname(member.getNickname());
        // v.setMainimage(vo.getFilename());
        orderproductvo.setName(vo.getName());
        System.out.println(orderproductvo);
        // System.out.println(v);

        int ok = boardService.insert(v);
        String ment = "정상적으로 저장되지 않았습니다. 다시 시도해주세요";
        if (ok > 0) {
            ment = "정상적으로 저장이 되었습니다";
        }
        mav.addObject("ment", ment);
        mav.addObject("orderproductvo", orderproductvo);
        mav.addObject("boardvo", v);
        mav.addObject("ok", ok);
        mav.setViewName("product/reviewsave");
        return mav;
    }

    // 리뷰읽기용
    @ResponseBody
    @RequestMapping("order/reviewreadpage")
    public ModelAndView reviewRead(@RequestBody BoardVO vo, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardvo = orderService.selectreviewboard(vo.getOrderproduct_id());
        ProductVO productvo = productService.selectProduct(boardvo.getProduct_id());
        System.out.println(boardvo);
        mav.addObject("boardvo", boardvo);
        mav.addObject("productvo", productvo);
        mav.setViewName("product/reviewreadpage");
        return mav;
    }

    // 교환환불 페이지 보여주기용
    @ResponseBody
    @RequestMapping("order/changeandrefundpage")
    public ModelAndView ChangeAndRefundPage(@RequestBody OrderProductVO v, HttpSession session) {
        ModelAndView mav = new ModelAndView();

        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        System.out.println(member.getMember_no());
        System.out.println(v);
        OrderProductVO vo = orderService.selectorderproduct(v.getOrderproduct_id(), member.getMember_no());
        vo.setName(v.getName());
        vo.setFilename(v.getFilename());
        mav.addObject("orderproductvo", vo);
        mav.setViewName("product/changeandrefundpage");
        return mav;
    }

    // 교환환불 페이지 저장용.
    @ResponseBody
    @RequestMapping("order/insertchangeandrefund")
    public void ChangeAndRefundInsert(@RequestBody ChangeAndRefundDTO dto, HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("userinfo");
        dto.setOrderproduct_id(dto.getOrderproduct_id());
        dto.setReasoncode(dto.getReasoncode());
        dto.setTextreason(dto.getTextreason());
        dto.setOrder_status_cd(dto.getOrder_status_cd());
        dto.setMember_no(member.getMember_no());
        orderService.ChangeAndRefundInsert(dto);
    }

    /*
     * public int ChangeAndRefundInsert(ChangeAndRefundDTO dto) {
     * return ordermapper.ChangeAndRefundInsert(dto);
     * }
     */

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
