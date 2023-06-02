package com.cocofarm.andapp.board;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
    private int board_no;
    private int rownum;
    private int member_no;
    private String nickname;
    private int board_category_cd;
    private int product_id;
    private String title;
    private String content;
    private Date regdate;
    private Date upddate;
    private int replycnt;
}
