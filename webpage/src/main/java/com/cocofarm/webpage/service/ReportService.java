package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
