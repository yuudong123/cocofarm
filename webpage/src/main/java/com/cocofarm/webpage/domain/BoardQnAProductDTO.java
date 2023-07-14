package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BoardQnAProductDTO implements Serializable {
    private int board_no;
    private int member_no;
    private String nickname;
    private int board_category_cd;
    private int product_id;
    private String title;
    private String content;
    private Date regdate;
    private Date upddate;
    private String mainimage;
    private int replycnt;
    private String product_name;
    private String product_content;
    private String answercontent;
}
