package com.cocofarm.webpage.service;

import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_REVIEW;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardQnAProductDTO;
import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.mapper.BoardMapper;
import com.cocofarm.webpage.mapper.ReplyMapper;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    ReplyMapper replyMapper;

    public int getTotal(CriteriaDTO cri) {
        return boardMapper.getTotal(cri);
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
        ArrayList<BoardVO> list = boardMapper.selectList(cri);
        return list;
    }

    public ArrayList<BoardVO> selectListAnd(CriteriaDTO cri) {
        ArrayList<BoardVO> list = boardMapper.selectList(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectQnaList(CriteriaDTO cri) {
        cri.setCode(BOARD_CATEGORY_QNA);
        ArrayList<QnaDTO> list = boardMapper.selectQnaList(cri);
        return list;
    }
    //제품쪽
       public ArrayList<BoardQnAProductDTO> selectQnaListByProduct(CriteriaDTO cri) {
        cri.setCode(BOARD_CATEGORY_QNA);
        ArrayList<BoardQnAProductDTO> list = boardMapper.selectQnaListByProduct(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectReviewList(CriteriaDTO cri) {
        cri.setCode(BOARD_CATEGORY_REVIEW);
        ArrayList<QnaDTO> list = boardMapper.selectReviewList(cri);
        return list;
    }

    // 제품쪽
    public ArrayList<QnaDTO> selectReviewListByProduct(CriteriaDTO cri) {
        cri.setCode(BOARD_CATEGORY_REVIEW);
        ArrayList<QnaDTO> list = boardMapper.selectReviewListByProduct(cri);
        return list;
    }

    public ArrayList<QnaDTO> selectNoAnsweredQnaList() {
        ArrayList<QnaDTO> list = boardMapper.selectNoAnsweredQnaList();
        return list;
    }

    public BoardVO selectboard(int board_no) {
        BoardVO vo = boardMapper.selectboard(board_no);
        return vo;
    }

    public QnaDTO selectqna(int board_no) {
        QnaDTO dto = boardMapper.selectqna(board_no);
        dto.setAnswer(replyMapper.selectAnswer(board_no));
        return dto;
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

    public ArrayList<BoardVO> eventBanner(int board_category_cd) {
        return boardMapper.eventBanner(board_category_cd);
    }
}
