package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.service.ReplyService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "reply/*")
@SessionAttributes("userinfo")
public class WebReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping(value = "/getlist", produces = "text/html;charset=utf-8")
    public String getList(@RequestBody HashMap<String, Integer> param, HttpSession session) {
        MemberVO viewer = (MemberVO) session.getAttribute("userinfo");
        ArrayList<String> replylist = replyService.selectListWeb(param.get("board_no"), viewer);
        return new Gson().toJson(replylist);
    }

    @PostMapping(value = "/getanswer", produces = "text/html;charset=utf-8")
    public String getAnswer(@RequestBody HashMap<String, Integer> param) {
        ReplyVO replyVO = replyService.selectAnswer(param.get("board_no"));
        return new Gson().toJson(replyVO);
    }

    @PostMapping(value = "/write", produces = "text/html;charset=utf-8")
    public String write(@RequestBody ReplyVO replyVO, HttpSession session) {
        MemberVO memberVO = (MemberVO) session.getAttribute("userinfo");
        replyVO.setMember_no(memberVO.getMember_no());
        replyVO.setNickname(memberVO.getNickname());
        return replyService.insert(replyVO) + "";
    }

    @PostMapping(value = "/modify", produces = "text/html;charset=utf-8")
    public String modify(@RequestBody ReplyVO replyVO) {
        System.out.println(replyVO);
        return replyService.update(replyVO) + "";
    }

    @PostMapping(value = "/delete", produces = "text/html;charset=utf-8")
    public String delete(@RequestBody HashMap<String, Integer> param) {
        return replyService.delete(param.get("reply_no")) + "";
    }
}
