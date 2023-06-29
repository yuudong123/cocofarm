package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class OrderProductDTO implements Serializable {
    private int order_id;
    private ArrayList<OrderProductVO> orderproduct_list;

    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;
    private String value;
}
