package com.cocofarm.andapp.order;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {
    private int order_id;
    private int member_no;
    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;

}
