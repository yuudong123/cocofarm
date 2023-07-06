package com.cocofarm.webpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ReportVO;
import com.cocofarm.webpage.service.BoardService;
import com.cocofarm.webpage.service.MemberService;
import com.cocofarm.webpage.service.ReplyService;
import com.cocofarm.webpage.service.ReportService;
import com.google.gson.Gson;

@SessionAttributes("userinfo")
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @PostMapping("/report")
    public int insertReport(@RequestBody ReportVO vo, Model model) {
        MemberVO reporter = (MemberVO) model.getAttribute("userinfo");
        if (reporter != null) {
            vo.setMember_no(reporter.getMember_no());
            return reportService.insertReport(vo);
        }
        return 0;
    }

    @PostMapping("/getreportboard")
    public String selectReportBoard(@RequestBody int report_no) {
        ReportVO reportVO = reportService.selectReportBoard(report_no);
        return new Gson().toJson(reportVO);
    }

    @PostMapping("/getreportreply")
    public String selectReportReply(@RequestBody int report_no) {
        ReportVO reportVO = reportService.selectReportReply(report_no);
        return new Gson().toJson(reportVO);
    }

    @Transactional
    @PostMapping("/banwriter")
    public String banwriter(@RequestBody ReportVO report) {
        int ban = memberService.banned(report.getReported_email(), "N");
        int process = reportService.processReport(report.getReport_no());
        if (ban == 1 && process == 1) {
            return "success";
        } else {
            return "failed";
        }
    }

    @Transactional
    @PostMapping("/deleteboard")
    public String deleteBoard(@RequestBody ReportVO report) {
        int delete = boardService.delete(report.getReported_board());
        int process = reportService.processReport(report.getReport_no());
        if (delete == 1 && process == 1) {
            return "success";
        } else {
            return "failed";
        }
    }

    @Transactional
    @PostMapping("/deletereply")
    public String deleteReply(@RequestBody ReportVO report) {
        int delete = replyService.delete(report.getReported_reply());
        int process = reportService.processReport(report.getReport_no());
        if (delete == 1 && process == 1) {
            return "success";
        } else {
            return "failed";
        }
    }

    @Transactional
    @PostMapping("/bananddeleteboard")
    public String banAndDeleteBoard(@RequestBody ReportVO report) {
        int ban = memberService.banned(report.getReported_email(), "N");
        int delete = boardService.delete(report.getReported_board());
        int process = reportService.processReport(report.getReport_no());
        if (ban == 1 && delete == 1 && process == 1) {
            return "success";
        } else {
            return "failed";
        }
    }

    @Transactional
    @PostMapping("/bananddeletereply")
    public String banAndDeleteReply(@RequestBody ReportVO report) {
        int ban = memberService.banned(report.getReported_email(), "N");
        int delete = replyService.delete(report.getReported_reply());
        int process = reportService.processReport(report.getReport_no());
        if (ban == 1 && delete == 1 && process == 1) {
            return "success";
        } else {
            return "failed";
        }
    }

    @PostMapping("/ignorereport")
    public int ignoreReport(@RequestBody int report_no) {
        return reportService.processReport(report_no);
    }
}
