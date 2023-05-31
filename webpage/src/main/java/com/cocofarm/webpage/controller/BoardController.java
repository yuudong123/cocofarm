package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.service.BoardService;
import com.google.gson.Gson;

@Controller
public class BoardController {

    @Autowired
    BoardService service;

    @PostMapping(value = "selectlist", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectList(int code) {
        ArrayList<BoardVO> list = service.selectList(code);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "select", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String select(int board_no) {
        BoardVO vo = service.select(board_no);
        return new Gson().toJson(vo);
    }

    @PostMapping(value = "insert", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void insert(BoardVO vo) {
        service.insert(vo);
    }

    @PostMapping(value = "update", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void update(BoardVO vo) {
        service.update(vo);
    }

    @PostMapping(value = "delete", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void delete(int board_no) {
        service.delete(board_no);
    }

}
