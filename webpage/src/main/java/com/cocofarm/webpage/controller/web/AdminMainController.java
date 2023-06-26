package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ProductService;
import com.google.gson.Gson;

@Controller
@RequestMapping("admin/*")
@SessionAttributes("userinfo")
public class AdminMainController {

    @Autowired
    BoardService boardService;

    @Autowired
    ProductService productService;

    @GetMapping(value = "/home")
    public ModelAndView adminHome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminhome");
        return mav;
    }

    @GetMapping(value = "/product")
    public ModelAndView adminProduct() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/");
        return mav;
    }

    @GetMapping(value = "/order")
    public ModelAndView adminOrder() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/");
        return mav;
    }

    @GetMapping(value = "/board")
    public ModelAndView adminBoard() {
        ModelAndView mav = new ModelAndView();
        ArrayList<ProductVO> productlist = productService.selectProductListWithImage();
        mav.setViewName("board/admin/main");
        mav.addObject("productlist", productlist);
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/board")
    public String adminQnaList() {
        ArrayList<QnaDTO> qnalist = boardService.selectNoAnsweredQnaList();
        return new Gson().toJson(qnalist);
    }

    @GetMapping(value = "/board/write")
    public ModelAndView adminBoardWriteGET() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/admin/write");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/board/write")
    public String adminBoardWritePOST(BoardVO boardVO, HttpSession session) {
        MemberVO memberVO = (MemberVO) session.getAttribute("userinfo");
        boardVO.setMember_no(memberVO.getMember_no());
        boardVO.setNickname(memberVO.getNickname());
        int result = boardService.insert(boardVO);
        if (result == 1) {
            return "등록되었습니다.";
        } else {
            return "작성에 실패했습니다.";
        }
    }

    @GetMapping(value = "/member")
    public ModelAndView adminMember() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/");
        return mav;
    }
}
