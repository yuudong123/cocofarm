package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.PageDTO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.domain.ReportVO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ImageService;
import com.cocofarm.webpage.service.ProductService;
import com.cocofarm.webpage.service.ReportService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/admin/board/*")
@SessionAttributes("userinfo")
public class AdminBoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @Autowired
    ReportService reportService;

    @GetMapping(value = { "/qna", "/" })
    public ModelAndView boardQna() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/admin/qna");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/newqnalist")
    public String newQnaList() {
        ArrayList<QnaDTO> qnalist = boardService.selectNoAnsweredQnaList();
        return new Gson().toJson(qnalist);
    }

    @GetMapping(value = "/boardreport")
    public ModelAndView boardReport() {
        ModelAndView mav = new ModelAndView();
        ArrayList<ReportVO> reportlist = reportService.selectReportList("board");
        mav.addObject("reportlist", reportlist);
        mav.setViewName("/board/admin/reportboard");
        return mav;
    }

    @GetMapping(value = "/replyreport")
    public ModelAndView replyReport() {
        ModelAndView mav = new ModelAndView();
        ArrayList<ReportVO> reportlist = reportService.selectReportList("reply");
        mav.addObject("reportlist", reportlist);
        mav.setViewName("/board/admin/reportreply");
        return mav;
    }

    @GetMapping(value = "/reporthistory")
    public ModelAndView reportHistory(CriteriaDTO cri) {
        ModelAndView mav = new ModelAndView();
        int total = reportService.getHistoryTotal(cri);
        ArrayList<ReportVO> reportlist = reportService.selectReportHistory(cri);
        mav.addObject("reportlist", reportlist);
        mav.addObject("pager", new PageDTO(cri, total));
        mav.setViewName("board/admin/reporthistory");
        return mav;
    }

    @GetMapping(value = "/write")
    public ModelAndView writeGET() {
        ModelAndView mav = new ModelAndView();
        ArrayList<ImageDTO> imagelist = imageService.selectAllImageWithProductId(0);
        mav.addObject("imagelist", imagelist);
        mav.setViewName("board/admin/write");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/write")
    public String writePOST(@RequestBody BoardVO boardVO, HttpSession session) {
        MemberVO memberVO = (MemberVO) session.getAttribute("userinfo");
        boardVO.setMember_no(memberVO.getMember_no());
        boardVO.setNickname(memberVO.getNickname());
        if (boardVO.getMainimage() == null) {
            boardVO.setMainimage("main-logo.png");
        }
        int result = boardService.insert(boardVO);
        if (result == 1) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "/{board_no}/modify")
    public ModelAndView modifyGET(@PathVariable int board_no) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardVO = boardService.selectboard(board_no);
        ArrayList<ImageDTO> imagelist = imageService.selectAllImageWithProductId(0);
        mav.addObject("board", boardVO);
        mav.addObject("imagelist", imagelist);
        mav.setViewName("board/admin/modify");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/{board_no}/modify")
    public String writePOST(@RequestBody BoardVO boardVO, @PathVariable int board_no) {
        if (boardVO.getMainimage() == null) {
            boardVO.setMainimage("main-logo.png");
        }
        boardVO.setBoard_no(board_no);
        int result = boardService.update(boardVO);
        if (result == 1) {
            return "success";
        } else {
            return "failure";
        }
    }
}
