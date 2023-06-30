package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.QnaDTO;

@Mapper
public interface BoardMapper {

    public int getTotal(CriteriaDTO cri);

    public ArrayList<BoardVO> selectList(CriteriaDTO cri);

    public ArrayList<QnaDTO> selectQnaList(CriteriaDTO cri);

    public ArrayList<QnaDTO> selectReviewList(CriteriaDTO cri);

    public ArrayList<QnaDTO> selectNoAnsweredQnaList();

    public BoardVO selectboard(int board_no);

    public QnaDTO selectqna(int board_no);

    public int insert(BoardVO vo);

    public int update(BoardVO vo);

    public int delete(int board_no);

    public ArrayList<BoardVO> eventBanner(int board_category_cd);

}
