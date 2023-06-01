package com.cocofarm.andapp.board;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
    private int board_no;
    private int member_no;
    private int board_category_cd;
    private int product_id;
    private String title;
    private String content;
    private Date regdate;
    private Date upddate;
}
