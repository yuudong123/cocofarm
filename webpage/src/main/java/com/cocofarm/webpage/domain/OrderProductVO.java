package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OrderProductVO implements Serializable {
    private int order_product_id;
    private int product_id;
    private int order_id;
    private int member_no;
    private int amount;
    private int price;
    private int order_status_cd;
    private String filename;
    private Date orderdate;
    private String name;
    private String value;
}