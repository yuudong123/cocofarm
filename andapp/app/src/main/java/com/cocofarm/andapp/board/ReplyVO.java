package com.cocofarm.andapp.board;

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
