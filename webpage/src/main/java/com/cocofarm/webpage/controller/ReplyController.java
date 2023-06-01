package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.service.ReplyService;
import com.google.gson.Gson;

@Controller
@RequestMapping
public class ReplyController {

    @Autowired
    ReplyService service;

    @PostMapping(value = "insertreply")
    public void insertReply(ReplyVO vo) {
        service.insert(vo);
    }

    @PostMapping(value = "updatereply")
    public void updateReply(ReplyVO vo) {
        service.update(vo);
    }

    @PostMapping(value = "deletereply")
    public void deleteReply(int reply_no) {
        service.delete(reply_no);
    }

    @PostMapping(value = "selectreplylist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectReplyListAnd(int board_no) {
        ArrayList<ReplyVO> list = service.selectList(board_no);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "insertreply.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void insertReplyAnd(ReplyVO vo) {
        service.insert(vo);
    }

    @PostMapping(value = "updatereply.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void updateReplyAnd(ReplyVO vo) {
        service.update(vo);

    }

    @PostMapping(value = "deletereply.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void deleteReplyAnd(int reply_no) {
        service.delete(reply_no);
    }
}
