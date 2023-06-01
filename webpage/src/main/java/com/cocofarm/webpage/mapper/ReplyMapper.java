package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ReplyVO;

@Mapper
public interface ReplyMapper {
    public ArrayList<ReplyVO> selectList(int board_no);

    public int insert(ReplyVO vo);

    public int update(ReplyVO vo);

    public int delete(int reply_no);

    public void deleteAll(int board_no);
}
