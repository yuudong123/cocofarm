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

  public int sns_out(String email) {
    return mapper.sns_out(email);
  }

  public MemberVO am_modify(MemberVO vo) {
    mapper.am_modify(vo);
    return mapper.login(vo);
  }

  public ArrayList<BoardVO> myboard_list(int member_no) {
    ArrayList<BoardVO> list = mapper.myboard_list(member_no);
    return list;
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

  public int countAll(CriteriaDTO cri) {
    return mapper.countAll(cri);
  }

  public int countBanned(CriteriaDTO cri) {
    return mapper.countBanned(cri);
  }

  // Admin
  public ArrayList<MemberVO> memberListAll(CriteriaDTO cri) {
    ArrayList<MemberVO> list = mapper.memberListAll(cri);
    return list;
  }

  public ArrayList<MemberVO>  memberListBanned(CriteriaDTO cri) {
  ArrayList<MemberVO> list = mapper.memberListBanned(cri);
  return list;
  }

  public int banned(String email, String isactivated) {
    return mapper.banned(email, isactivated);
  }

}
