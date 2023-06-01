package com.cocofarm.webpage.domain;

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

// create table tbl_board (
// board_no number,
// member_no number,
// board_category_code number,
// product_id number,
// title nvarchar2(100),
// content nvarchar2(1000),
// regdate date,
// upddate date,
// constraint pk_tbl_board primary key (board_no),
// constraint fk_board_member foreign key (member_no) references tbl_member
// (member_no),
// constraint fk_board_product foreign key (product_id) references tbl_product
// (product_id),
// constraint fk_board_category_cd foreign key (board_category_cd) references
// tbl_code (code)
// )
