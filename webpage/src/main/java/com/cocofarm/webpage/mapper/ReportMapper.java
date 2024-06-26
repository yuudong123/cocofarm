package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.ReportVO;

@Mapper
public interface ReportMapper {

    public ArrayList<ReportVO> selectReportList(String category);

    public int insertReport(ReportVO reportVO);

    public ReportVO selectReportBoard(int report_no);
    public ReportVO selectReportReply(int report_no);

    public int processReport(ReportVO report);

    public int getHistoryTotal(CriteriaDTO cri);

    public ArrayList<ReportVO> selectReportHistory(CriteriaDTO cri);

}
