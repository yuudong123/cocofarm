package com.cocofarm.webpage.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDTO {

    private String order_id;
    private int member_no;

    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;

    private String value;
}
