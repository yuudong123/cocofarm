package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ChangeAndRefundDTO implements Serializable {
    private int changeandrefund_id;
    private int orderproduct_id;
    private Date candrdate;
    private int reasoncode;
    private String textreason;

    private int amount;
    private int price;
    private int product_id;
    private int member_no;
    private int order_status_cd;
}

/*
 * create table tbl_changeandrefund(
 * changeandrefund_id number,
 * orderproduct_id number(30),
 * candrdate date,
 * reasoncode number,
 * textreason varchar2(1000),
 * constraint pk_tbl_changeandrefund primary key (changeandrefund_id)
 * );
 */
