package com.cocofarm.webpage.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReplyVO implements Serializable {
    private int reply_no;
    private int member_no;
    private String nickname;
    private int board_no;
    private String content;
    private String regdate;
    private String upddate;
}

// create table tbl_reply (
// reply_no number,
// member_no member,
// board_no number not null,
// content nvarchar2(1000) not null,
// regdate date not null,
// upddate date not null,
// constraint pk_tbl_reply primary key (reply_no, member_no),
// )