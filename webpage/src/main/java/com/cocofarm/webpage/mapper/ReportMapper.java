package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ReportVO;

@Mapper
public interface ReportMapper {

    public ArrayList<ReportVO> selectReportList(String category);

    public int insertReport(ReportVO reportVO);

}
