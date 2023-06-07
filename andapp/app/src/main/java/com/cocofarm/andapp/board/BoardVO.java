package com.cocofarm.andapp.board;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BoardVO implements Serializable {
    private int board_no;
    private int rownum;
    private int member_no;
    private String nickname;
    private int board_category_cd;
    private int product_id;
    private String title;
    private String content;
    private String regdate;
    private String upddate;
    private int replycnt;
}
