package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.QnaDTO;
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

  public String email_search(String email) {
    return mapper.email_search(email);
  }

  public MemberVO sns_login(MemberVO vo) {
    return mapper.sns_login(vo);
  }

  public MemberVO am_modify(MemberVO vo) {
    mapper.am_modify(vo);
    return mapper.login(vo);
  }

  public void web_modify(MemberVO vo) {
    mapper.web_modify(vo);
  }

  public void pw_modify(String email, String password) {
    mapper.pw_modify(email, password);
  }

  public int away(String email) {
    return mapper.away(email);
  }

  public ArrayList<QnaDTO> myboard(QnaDTO dto) {
    ArrayList<QnaDTO> result = mapper.myboard(dto);

    return result;
  }

  // 내가 쓴 리뷰 보기
  public ArrayList<BoardVO> myreviewboard(MemberVO vo) {
    return mapper.myreviewboard(vo);
  }

  public int countAll() {
    return mapper.countAll();
  }

  public int countBanned() {
    return mapper.countBanned();
  }

  // Admin
  public ArrayList<MemberVO> memberListAll(CriteriaDTO cri) {
    ArrayList<MemberVO> list = mapper.memberListAll(cri);
    return list;
  }

  public ArrayList<MemberVO>  memberListBanned(CriteriaDTO cri) {
  ArrayList<MemberVO> list = mapper.memberListBanned();
  return list;
  }

  public void banned(String email, String isactivated) {
    mapper.banned(email, isactivated);
  }

}
