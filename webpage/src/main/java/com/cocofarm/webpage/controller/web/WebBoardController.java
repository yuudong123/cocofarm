package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.PageDTO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ProductService;
import com.cocofarm.webpage.service.ReplyService;

@Controller
@SessionAttributes("userinfo")
public class WebBoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ProductService productService;

    @GetMapping(value = "/board/{category}")
    public ModelAndView selectBoardList(@PathVariable String category, CriteriaDTO cri) {
        ModelAndView mav = new ModelAndView();
        if (category.equals("qna")) {
            ArrayList<QnaDTO> qnalist = boardService.selectQnaList(cri);
            ArrayList<ProductVO> productlist = productService.selectProductListWithImage();
            mav.addObject("boardlist", qnalist);
            mav.addObject("productlist", productlist);
            mav.setViewName("board/qnalist");
        } else if (category.equals("review")) {
            ArrayList<QnaDTO> reviewlist = boardService.selectReviewList(cri);
            ArrayList<ProductVO> productlist = productService.selectProductListWithImage();
            mav.addObject("boardlist", reviewlist);
            mav.addObject("productlist", productlist);
            mav.setViewName("board/reviewlist");
        } else {
            ArrayList<BoardVO> boardlist = boardService.selectList(category, cri);
            mav.addObject("boardlist", boardlist);
            mav.setViewName("board/boardlist");
        }
        int total = boardService.getTotal(cri);
        PageDTO pagedto = new PageDTO(cri, total);
        mav.addObject("pager", pagedto);
        mav.addObject("category", category);
        return mav;
    }

    @GetMapping(value = { "/board/notice/{board_no}", "/board/event/{board_no}" })
    public ModelAndView selectBoard(@PathVariable int board_no) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardvo = boardService.selectboard(board_no);
        mav.addObject("boardvo", boardvo);
        mav.setViewName("board/boardread");
        return mav;
    }

    @GetMapping(value = "/board/qna/{board_no}")
    public ModelAndView selectQna(@PathVariable int board_no) {
        ModelAndView mav = new ModelAndView();
        QnaDTO qnaDTO = boardService.selectqna(board_no);
        mav.addObject("qnadto", qnaDTO);
        mav.setViewName("board/qnaread");
        return mav;
    }

    @GetMapping(value = "/board/qnawrite")
    public ModelAndView insertQna() {
        ModelAndView mav = new ModelAndView();
        ArrayList<ProductVO> productlist = productService.selectProductListWithImage();
        mav.addObject("productlist", productlist);
        mav.setViewName("board/qnawrite");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/board/qnawrite")
    public int insertQna(@RequestBody BoardVO vo, Model model) {
        MemberVO member = (MemberVO) model.getAttribute("userinfo");
        if (member != null) {
            vo.setMember_no(member.getMember_no());
            vo.setNickname(member.getNickname());
        }
        return boardService.insert(vo);
    }

    @Transactional
    @ResponseBody
    @PostMapping(value = "/{board_no}/delete")
    public int deleteBoard(@PathVariable int board_no) {
        replyService.deleteAll(board_no);
        return boardService.delete(board_no);
    }
}
