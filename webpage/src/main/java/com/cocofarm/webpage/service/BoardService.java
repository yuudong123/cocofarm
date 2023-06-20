package com.cocofarm.webpage.service;

import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_REVIEW;

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
    BoardMapper mapper;

    public int getTotal(CriteriaDTO cri) {
        return mapper.getTotal(cri);
    }

    public ArrayList<BoardVO> selectList(String category, CriteriaDTO cri) {
        int code = 0;
        if (category.equals("notice")) {
            code = BOARD_CATEGORY_NOTICE;
        } else if (category.equals("event")) {
            code = BOARD_CATEGORY_EVENT;
        } else if (category.equals("review")) {
            code = BOARD_CATEGORY_REVIEW;
        }
        cri.setCode(code);
        ArrayList<BoardVO> list = mapper.selectList(cri);
        return list;
    }

    public ArrayList<BoardVO> selectListAnd(CriteriaDTO cri) {
        ArrayList<BoardVO> list = mapper.selectList(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectQnaList(CriteriaDTO cri) {
        ArrayList<QnaDTO> list = mapper.selectQnaList(cri);
        cri.setCode(201);
        return list;
    }

    public BoardVO selectboard(int board_no) {
        BoardVO vo = mapper.selectboard(board_no);
        return vo;
    }

    public QnaDTO selectqna(int board_no) {
        QnaDTO dto = mapper.selectqna(board_no);
        return dto;
    }

    public int insert(BoardVO vo) {
        return mapper.insert(vo);
    }

    public int update(BoardVO vo) {
        return mapper.update(vo);
    }

    public int delete(int board_no) {
        return mapper.delete(board_no);
    }

    public ArrayList<BoardVO> eventBanner(int board_category_cd) {
        return mapper.eventBanner(board_category_cd);
    }
}
