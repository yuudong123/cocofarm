package com.cocofarm.webpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.mapper.MemberMapper;

@Service
public class MemberService {
    
    @Autowired
    MemberMapper mapper;

    public MemberVO login(MemberVO vo) {   
      return mapper.login(vo);
    }

    public int join(MemberVO vo) {
      return mapper.join(vo);
    }

    public MemberVO am_modify(MemberVO vo) {
      mapper.am_modify(vo);
      return mapper.login(vo);
    }
}
