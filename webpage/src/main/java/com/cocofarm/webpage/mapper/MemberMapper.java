package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.MemberVO;

@Mapper
public interface MemberMapper {
    public MemberVO login(MemberVO vo);
    public int join(MemberVO vo);
    public void am_modify(MemberVO vo);
    public int away(String email);
    public ArrayList<BoardVO> myboard(BoardVO vo);
}
