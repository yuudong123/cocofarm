package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ReportVO;

@Mapper
public interface ReportMapper {

    ArrayList<ReportVO> selectReportList(String category);

}
