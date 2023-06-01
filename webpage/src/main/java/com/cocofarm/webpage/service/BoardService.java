package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.mapper.BoardMapper;

@Service
public class BoardService {

    @Autowired
    BoardMapper mapper;

    public ArrayList<BoardVO> selectList(int code) {
        ArrayList<BoardVO> list = mapper.selectList(code);
        return list;
    }

    public BoardVO select(int board_no) {
        BoardVO vo = mapper.select(board_no);
        return vo;
    }

    public void insert(BoardVO vo) {
        mapper.insert(vo);
    }

    public void update(BoardVO vo) {
        mapper.update(vo);
    }

    public void delete(int board_no) {
        mapper.delete(board_no);
    }

}
