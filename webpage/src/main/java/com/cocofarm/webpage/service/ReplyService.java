package com.cocofarm.webpage.service;

import static com.cocofarm.webpage.common.CodeTable.MEMBER_TYPE_ADMIN;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.mapper.ReplyMapper;

@Service
public class ReplyService {

    @Autowired
    ReplyMapper mapper;

    public ArrayList<ReplyVO> selectList(int board_no) {
        ArrayList<ReplyVO> list = mapper.selectList(board_no);
        return list;
    }

    public ArrayList<String> selectListWeb(int board_no, MemberVO memberVO) {

        ArrayList<String> result = new ArrayList<>();

        ArrayList<ReplyVO> list = mapper.selectList(board_no);
        for (ReplyVO replyVO : list) {
            String buttons = "";
            if (memberVO != null) {
                if (memberVO.getMember_type_cd() == MEMBER_TYPE_ADMIN
                        || memberVO.getMember_no() == replyVO.getMember_no()) {
                    buttons = "<button class='btn btnmodify blue' onclick='modifyReply(" + replyVO.getReply_no()
                            + ",\"" + replyVO.getContent()
                            + "\")'>수정</button><button class='btn btndelete red' onclick='deleteReply("
                            + replyVO.getReply_no() + ")'>삭제</button>";
                }
                buttons += "<button class='btn-flat btnreport waves-effect waves-red modal-trigger' onclick='reportReply("
                        + replyVO.getReply_no() + ",\"" + replyVO.getNickname() + "\",\"" + replyVO.getMember_no()
                        + "\")' href='#reportModal'>신고</button>";
            }
            String reply = "<table><thead><tr><th>" + replyVO.getNickname() + "</th><th>" +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(replyVO.getRegdate()) +
                    "</th></tr></thead><tbody><td><span class='reply' th:data-no='" +
                    replyVO.getReply_no() + "'>" + replyVO.getContent() + "</span></td><td></td></tbody>" + buttons
                    + "</table>";
            result.add(reply);
        }
        return result;
    }

    public ReplyVO selectAnswer(int board_no) {
        ReplyVO vo = mapper.selectAnswer(board_no);
        return vo;
    }

    public int insert(ReplyVO vo) {
        return mapper.insert(vo);
    }

    public int update(ReplyVO vo) {
        return mapper.update(vo);
    }

    public int delete(int reply_no) {
        return mapper.delete(reply_no);
    }

    public void deleteAll(int board_no) {
        mapper.deleteAll(board_no);
    }

}
