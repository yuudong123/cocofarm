package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class ProductVO implements Serializable {
    private int product_id;
    private String name;
    private String content;
    private int price;
    private int amount;

    private Date regdate;
    private String isdeleted;
    private int product_category_cd;
    private String filename;
    private ArrayList<ImageDTO> img;
    private ArrayList<MemberVO> membervo;
    private int member_no;

}

/*
 * create table tbl_orderproduct(
 * orderproduct_id number,
 * product_id number,
 * order_id number,
 * member_no number,
 * amount number,
 * price number,
 * constraint pk_tbl_orderproduct primary key (orderproduct_id)
 * );
 */