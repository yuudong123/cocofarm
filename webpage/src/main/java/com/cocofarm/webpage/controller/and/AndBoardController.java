package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.ReplyService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userinfo")
public class AndBoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @PostMapping(value = "/board/getTotal.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getTotalAnd(CriteriaDTO cri) {
        if (cri.getKeyword() == null) {
            cri.setKeyword("");
        }
        return boardService.getTotal(cri) + "";
    }

    @PostMapping(value = "/board/selectboardlist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardListAnd(CriteriaDTO cri) {
        if (cri.getKeyword() == null) {
            cri.setKeyword("");
        }
        ArrayList<BoardVO> list = boardService.selectListAnd(cri);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/board/selectqnalist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectQnaListAnd(CriteriaDTO cri) {
        if (cri.getKeyword() == null) {
            cri.setKeyword("");
        }
        ArrayList<QnaDTO> list = boardService.selectQnaList(cri);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/board/selectboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectBoardAnd(int board_no) {
        BoardVO vo = boardService.selectboard(board_no);
        return new Gson().toJson(vo);
    }

    @PostMapping(value = "/board/selectqna.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectQnaAnd(int board_no) {
        QnaDTO dto = boardService.selectqna(board_no);
        return new Gson().toJson(dto);
    }

    @PostMapping(value = "/board/insertboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void insertBoardAnd(BoardVO vo) {
        boardService.insert(vo);
    }

    @PostMapping(value = "/board/updateboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void updateBoardAnd(BoardVO vo) {
        boardService.update(vo);
    }

    @PostMapping(value = "/board/deleteboard.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public void deleteBoardAnd(int board_no) {
        boardService.delete(board_no);
    }

    @PostMapping(value = "/board/eventbanner.and")
    @ResponseBody
    public String eventBanner(int board_category_cd) {
        return new Gson().toJson(boardService.eventBanner(board_category_cd));
    }
}
