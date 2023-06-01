package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void insert(ReplyVO vo) {
        mapper.insert(vo);
    }

    public void update(ReplyVO vo) {
        mapper.update(vo);
    }

    public void delete(int reply_no) {
        mapper.delete(reply_no);
    }

}
