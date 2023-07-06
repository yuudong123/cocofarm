package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.QnaDTO;

@Mapper
public interface MemberMapper {
    public MemberVO login(MemberVO vo);

    public int join(MemberVO vo);

    public String email_search(String email);

    public MemberVO sns_login(MemberVO vo);

    public void am_modify(MemberVO vo);

    public void web_modify(MemberVO vo);

    public void pw_modify(String email, String password);

    public int away(String email);

    public ArrayList<QnaDTO> myboard(QnaDTO dto);

    //내가 쓴 리뷰 보기
    public ArrayList<BoardVO> myreviewboard(MemberVO vo);

    public int countAll(CriteriaDTO cri);
    public int countBanned(CriteriaDTO cri);


    // Admin
    public ArrayList<MemberVO> memberListAll(CriteriaDTO cri);
    public ArrayList<MemberVO> memberListBanned(CriteriaDTO cri);
    
    public void banned(String email, String isactivated);
}
