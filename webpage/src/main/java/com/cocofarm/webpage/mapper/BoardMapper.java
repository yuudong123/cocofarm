package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.QnaDTO;

@Mapper
public interface BoardMapper {
    public ArrayList<BoardVO> selectList(int code);

    public ArrayList<QnaDTO> selectQnaList();

    public BoardVO select(int board_no);

    public int insert(BoardVO vo);

    public int update(BoardVO vo);

    public int delete(int board_no);
}
