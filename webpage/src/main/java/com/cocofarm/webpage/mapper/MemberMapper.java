package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.QnaDTO;

@Mapper
public interface MemberMapper {
    public MemberVO login(MemberVO vo);

    public int join(MemberVO vo);

    public String email_search(String email);

    public MemberVO sns_login(MemberVO vo);

    public void am_modify(MemberVO vo);

    public int away(String email);

    public ArrayList<QnaDTO> myboard(QnaDTO dto);
}
