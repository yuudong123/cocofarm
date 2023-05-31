package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.mapper.BoardMapper;

@Service
public class BoardServiceImp implements BoardService {

    @Autowired
    BoardMapper mapper;

    @Override
    public ArrayList<BoardVO> selectList(int code) {
        ArrayList<BoardVO> list = mapper.selectList(code);
        return list;
    }

    @Override
    public BoardVO select(int board_no) {
        BoardVO vo = mapper.select(board_no);
        return vo;
    }

    @Override
    public void insert(BoardVO vo) {
        mapper.insert(vo);
    }

    @Override
    public void update(BoardVO vo) {
        mapper.update(vo);
    }

    @Override
    public void delete(int board_no) {
        mapper.delete(board_no);
    }

}
