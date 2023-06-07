package com.cocofarm.webpage.domain;

import java.io.Serializable;

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
    private int replycnt; // join해서 가져올때만 사용
}

// create table tbl_board (
// board_no number,
// member_no number,
// nickname nvarchar2(50),
// board_category_code number,
// product_id number,
// title nvarchar2(100),
// content nvarchar2(1000),
// regdate date,
// upddate date,
// constraint pk_tbl_board primary key (board_no),
// )
