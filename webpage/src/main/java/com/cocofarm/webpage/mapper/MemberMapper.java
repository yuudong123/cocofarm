package com.cocofarm.webpage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.MemberVO;

@Mapper
public interface MemberMapper {
    public MemberVO login(MemberVO vo);
}
