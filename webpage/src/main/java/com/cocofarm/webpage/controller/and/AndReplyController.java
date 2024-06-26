package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.service.ReplyService;
import com.google.gson.Gson;

@RestController
@RequestMapping("reply/*")
public class AndReplyController {

    @Autowired
    ReplyService service;

    @PostMapping(value = "selectreplylist.and", produces = "text/html;charset=utf-8")
    public String selectReplyListAnd(int board_no) {
        ArrayList<ReplyVO> list = service.selectList(board_no);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "selectanswer.and", produces = "text/html;charset=utf-8")
    public String selectAnswerAnd(int board_no) {
        ReplyVO vo = service.selectAnswer(board_no);
        return new Gson().toJson(vo);
    }

    @PostMapping(value = "insertreply.and", produces = "text/html;charset=utf-8")
    public void insertReplyAnd(ReplyVO vo) {
        service.insert(vo);
    }

    @PostMapping(value = "updatereply.and", produces = "text/html;charset=utf-8")
    public void updateReplyAnd(ReplyVO vo) {
        service.update(vo);
    }

    @PostMapping(value = "deletereply.and", produces = "text/html;charset=utf-8")
    public void deleteReplyAnd(int reply_no) {
        service.delete(reply_no);
    }
}
