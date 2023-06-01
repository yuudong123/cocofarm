package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.service.BoardService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = { "notice", "qna", "review" })
public class BoardController {

    @Autowired
    BoardService service;

    @GetMapping(value = "selectboardlist")
    public ArrayList<BoardVO> selectBoardList(int code) {
        ArrayList<BoardVO> list = service.selectList(code);
        return list;
    }

    @GetMapping(value = "selectboard")
    public BoardVO selectBoard(int board_no) {
        BoardVO vo = service.select(board_no);
        return vo;
    }

    @GetMapping(value = "insertboard")
    public void insertBoard(BoardVO vo) {
        service.insert(vo);
    }

    @GetMapping(value = "updateboard")
    public void updateBoard(BoardVO vo) {
        service.update(vo);
    }

    @GetMapping(value = "deleteboard")
    public void deleteBoard(int board_no) {
        service.delete(board_no);
    }

    @PostMapping(value = "selectboardlist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardListAnd(int code) {
        ArrayList<BoardVO> list = service.selectList(code);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "selectboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardAnd(int board_no) {
        BoardVO vo = service.select(board_no);
        return new Gson().toJson(vo);
    }

    @PostMapping(value = "insertboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void insertBoardAnd(BoardVO vo) {
        service.insert(vo);
    }

    @PostMapping(value = "updateboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void updateBoardAnd(BoardVO vo) {
        service.update(vo);
    }

    @PostMapping(value = "deleteboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void deleteBoardAnd(int board_no) {
        service.delete(board_no);
    }

}
