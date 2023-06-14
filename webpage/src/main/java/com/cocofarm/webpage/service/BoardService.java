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

    public int getTotal(CriteriaDTO cri) {
        return boardMapper.getTotal(cri);
    }

    public ArrayList<BoardVO> selectList(CriteriaDTO cri) {
        ArrayList<BoardVO> list = boardMapper.selectList(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectQnaList(CriteriaDTO cri) {
        ArrayList<QnaDTO> list = boardMapper.selectQnaList(cri);
        return list;
    }

    public BoardVO selectboard(int board_no) {
        BoardVO vo = boardMapper.selectboard(board_no);
        return vo;
    }

    public BoardVO selectqna(int board_no) {
        BoardVO vo = boardMapper.selectqna(board_no);
        return vo;
    }

    public int insert(BoardVO vo) {
        return boardMapper.insert(vo);
    }

    public int update(BoardVO vo) {
        return boardMapper.update(vo);
    }

    public int delete(int board_no) {
        return boardMapper.delete(board_no);
    }

}
