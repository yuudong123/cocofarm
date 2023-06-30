package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class OrderProductDTO implements Serializable {
    private String order_id;
    private ArrayList<OrderProductVO> orderproduct_list;
    private int member_no;
    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;
    private String value;
}
