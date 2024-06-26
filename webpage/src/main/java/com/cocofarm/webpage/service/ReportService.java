package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.ReportVO;
import com.cocofarm.webpage.mapper.ReportMapper;

@Service
public class ReportService {

    @Autowired
    ReportMapper reportMapper;

    public ArrayList<ReportVO> selectReportList(String category) {
        ArrayList<ReportVO> reportlist = reportMapper.selectReportList(category);
        return reportlist;
    }

    public int insertReport(ReportVO reportVO) {
        return reportMapper.insertReport(reportVO);
    }

    public ReportVO selectReportBoard(int report_no) {
        ReportVO reportVO = reportMapper.selectReportBoard(report_no);
        return reportVO;
    }
    public ReportVO selectReportReply(int report_no) {
        ReportVO reportVO = reportMapper.selectReportReply(report_no);
        return reportVO;
    }

    public int processReport(ReportVO report) {
        return reportMapper.processReport(report);
    }

    public int getHistoryTotal(CriteriaDTO cri) {
        return reportMapper.getHistoryTotal(cri);
    }

    public ArrayList<ReportVO> selectReportHistory(CriteriaDTO cri) {
        return reportMapper.selectReportHistory(cri);
    }
}
