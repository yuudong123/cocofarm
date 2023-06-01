package com.cocofarm.webpage.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
    private int reply_no;
    private int member_no;
    private int board_no;
    private String content;
    private Date regdate;
    private Date upddate;
}

// create table tbl_reply (
// reply_no number,
// member_no member,
// board_no number not null,
// content nvarchar2(1000) not null,
// regdate date not null,
// upddate date not null,
// constraint pk_tbl_reply primary key (reply_no, member_no),
// constraint fk_reply_member foreign key (member_no) references tbl_member
// (member_no),
// constraint fk_reply_board foreign key (board_no) references tbl_board
// (board_no)
// )