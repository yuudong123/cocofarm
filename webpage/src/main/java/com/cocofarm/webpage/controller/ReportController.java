package com.cocofarm.webpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.ReportVO;
import com.cocofarm.webpage.service.ReportService;
import com.google.gson.Gson;

@SessionAttributes("userinfo")
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/report")
    public int insertReport(@RequestBody ReportVO vo, Model model) {
        MemberVO reporter = (MemberVO) model.getAttribute("userinfo");
        if (reporter != null) {
            vo.setMember_no(reporter.getMember_no());
            return reportService.insertReport(vo);
        }
        return 0;
    }

    @PostMapping("/getreport")
    public String selectReport(@RequestBody int report_no) {
        ReportVO reportVO = reportService.selectReport(report_no);
        return new Gson().toJson(reportVO);
    }
}
