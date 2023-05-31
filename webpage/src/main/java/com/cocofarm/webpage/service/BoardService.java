package com.cocofarm.webpage.service;

import java.util.ArrayList;

import com.cocofarm.webpage.domain.BoardVO;

public interface BoardService {
    public ArrayList<BoardVO> selectList(int code);

    public BoardVO select(int board_no);

    public void insert(BoardVO vo);

    public void update(BoardVO vo);

    public void delete(int board_no);
}
