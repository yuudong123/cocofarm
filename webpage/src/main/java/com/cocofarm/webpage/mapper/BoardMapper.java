package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;

@Mapper
public interface BoardMapper {
    public ArrayList<BoardVO> selectList(int code);

    public BoardVO select(int board_no);

    public void insert(BoardVO vo);

    public void update(BoardVO vo);

    public void delete(int board_no);
}
