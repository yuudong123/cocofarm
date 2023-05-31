package com.cocofarm.andapp.board;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
    int board_no;
    int member_no;
    int board_category_cd;
    int product_id;
    String title;
    String content;
    Date regdate;
    Date upddate;
}
