package com.cocofarm.webpage.domain;

import lombok.Data;

@Data
public class ReportVO {
    private int report_no;
    private int member_no;
    private int reported_board;
    private int reported_reply;
    private int reported_member;
    private int reason_cd;
    private String content;
}
