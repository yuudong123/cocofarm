package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.domain.UserDTO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ReplyService;
import com.google.gson.Gson;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = { "/notice/*", "/qna/*", "/review/*" })
@SessionAttributes("userinfo")
@AllArgsConstructor
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @GetMapping(value = "/")
    public ModelAndView selectBoardList(int code) {
        ModelAndView mav = new ModelAndView();
        ArrayList<BoardVO> boardlist = boardService.selectList(code);
        mav.addObject("boardlist", boardlist);
        mav.setViewName("/");
        return mav;
    }

    @GetMapping(value = "/{board_no}")
    public ModelAndView selectBoard(@PathVariable int board_no,
            @SessionAttribute("userinfo") @Nullable UserDTO userdto) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardvo = boardService.select(board_no);
        ArrayList<ReplyVO> replylist = replyService.selectList(board_no);
        mav.addObject("boardvo", boardvo);
        if (replylist.size() != 0) {
            mav.addObject("replylist", replylist);
        }
        if (userdto != null) {
            mav.addObject("viewer", userdto.getMember_no());
            mav.addObject("viewertype", userdto.getMember_type_cd());
        } else {
            mav.addObject("viewer", null);
            mav.addObject("viewertype", null);
        }
        mav.setViewName("board/read");
        return mav;
    }

    @GetMapping(value = "/write")
    public ModelAndView insertBoard() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/write");
        return mav;
    }

    @PostMapping(value = "/write")
    public void insertBoard(BoardVO vo) {
        boardService.insert(vo);
    }

    @GetMapping(value = "/{board_no}/modify")
    public ModelAndView updateBoard(@PathVariable int board_no) {
        ModelAndView mav = new ModelAndView();
        BoardVO boardvo = boardService.select(board_no);
        mav.addObject("boardvo", boardvo);
        mav.setViewName("board/modify");
        return mav;
    }

    @PostMapping(value = "/{board_no}/modify")
    public void updateBoard(@PathVariable int board_no, BoardVO vo) {
        vo.setBoard_no(board_no);
        boardService.update(vo);
    }

    @Transactional
    @PostMapping(value = "/{board_no}/delete")
    public void deleteBoard(int board_no) {
        replyService.deleteAll(board_no);
        boardService.delete(board_no);
    }

    @PostMapping(value = "selectboardlist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardListAnd(int code) {
        ArrayList<BoardVO> list = boardService.selectList(code);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "selectboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardAnd(int board_no) {
        BoardVO vo = boardService.select(board_no);
        return new Gson().toJson(vo);
    }

    @PostMapping(value = "insertboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void insertBoardAnd(BoardVO vo) {
        boardService.insert(vo);
    }

    @PostMapping(value = "updateboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void updateBoardAnd(BoardVO vo) {
        boardService.update(vo);
    }

    @PostMapping(value = "deleteboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void deleteBoardAnd(int board_no) {
        boardService.delete(board_no);
    }

}
