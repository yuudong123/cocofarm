package com.cocofarm.webpage.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReportVO {
    private int report_no;
    private int member_no;
    private String nickname;
    private int reported_board = 0;
    private String reported_title;
    private String reported_content;
    private int reported_reply = 0;
    private int reported_member;
    private String reported_email;
    private String reported_nickname;
    private int reason_cd;
    private String reason_text;
    private String content;
    private Date reportdate;
    private String isprocessed;
}
