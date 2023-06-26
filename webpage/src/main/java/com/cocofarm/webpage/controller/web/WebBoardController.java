package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.PageDTO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.domain.ReplyVO;
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
        } else {
            ArrayList<BoardVO> boardlist = boardService.selectList(category, cri);
            mav.addObject("boardlist", boardlist);
            mav.setViewName("board/boardlist");
        }
        int total = boardService.getTotal(cri);
        PageDTO pagedto = new PageDTO(cri, total);
        mav.addObject("pager", pagedto);
        return mav;
    }

    @GetMapping(value = { "notice/{board_no}", "event/{board_no}" })
    public ModelAndView selectBoard(@PathVariable int board_no,
            @SessionAttribute("userinfo") @Nullable MemberVO memberVO) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardvo = boardService.selectboard(board_no);
        ArrayList<ReplyVO> replylist = replyService.selectList(board_no);
        mav.addObject("boardvo", boardvo);
        if (replylist.size() != 0) {
            mav.addObject("replylist", replylist);
        }
        if (memberVO != null) {
            mav.addObject("viewer", memberVO.getMember_no());
            mav.addObject("viewertype", memberVO.getMember_type_cd());
        } else {
            mav.addObject("viewer", null);
            mav.addObject("viewertype", null);
        }
        mav.setViewName("board/boardread");
        return mav;
    }

    // @GetMapping(value = "/write")
    // public ModelAndView insertBoard() {
    // ModelAndView mav = new ModelAndView();
    // mav.setViewName("board/boardwrite");
    // return mav;
    // }

    @GetMapping(value = "/board/qnawrite")
    public ModelAndView insertQna() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/qnawrite");
        return mav;
    }

    // @PostMapping(value = "/write")
    // public int insertBoard(BoardVO vo) {
    // return boardService.insert(vo);
    // }

    @PostMapping(value = "/board/qnawrite")
    public int insertQna(BoardVO vo) {
        return boardService.insert(vo);
    }

    // @GetMapping(value = "/{board_no}/modify")
    // public ModelAndView updateBoard(@PathVariable int board_no) {
    // ModelAndView mav = new ModelAndView();
    // BoardVO boardvo = boardService.selectboard(board_no);
    // if (boardvo.getBoard_category_cd() == BOARD_CATEGORY_QNA) {
    // mav.setViewName("board/qnamodify");
    // } else {
    // mav.setViewName("board/boardmodify");
    // }
    // mav.addObject("boardvo", boardvo);
    // return mav;
    // }

    // @PostMapping(value = "/{board_no}/modify")
    // public int updateBoard(@PathVariable int board_no, BoardVO vo) {
    // vo.setBoard_no(board_no);
    // return boardService.update(vo);
    // }

    @Transactional
    @PostMapping(value = "/{board_no}/delete")
    public int deleteBoard(int board_no) {
        replyService.deleteAll(board_no);
        return boardService.delete(board_no);
    }
}
