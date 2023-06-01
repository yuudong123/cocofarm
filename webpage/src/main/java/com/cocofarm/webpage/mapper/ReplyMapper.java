package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ReplyVO;

@Mapper
public interface ReplyMapper {
    public ArrayList<ReplyVO> selectList(int board_no);

    public void insert(ReplyVO vo);

    public void update(ReplyVO vo);

    public void delete(int reply_no);
}
