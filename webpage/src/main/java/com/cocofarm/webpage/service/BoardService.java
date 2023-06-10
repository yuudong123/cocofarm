package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.mapper.BoardMapper;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    public ArrayList<BoardVO> selectList(int code) {
        ArrayList<BoardVO> list = boardMapper.selectList(code);
        return list;
    }

    public ArrayList<BoardVO> selectListCri(CriteriaDTO cri) {
        ArrayList<BoardVO> list = boardMapper.selectListCri(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectQnaList() {
        ArrayList<QnaDTO> list = boardMapper.selectQnaList();
        return list;
    }

    public BoardVO select(int board_no) {
        BoardVO vo = boardMapper.select(board_no);
        return vo;
    }

    public void insert(BoardVO vo) {
        boardMapper.insert(vo);
    }

    public void update(BoardVO vo) {
        boardMapper.update(vo);
    }

    public void delete(int board_no) {
        boardMapper.delete(board_no);
    }

}
