package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderVO implements Serializable {
    private String order_id;
    private int member_no;

    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;
    private List<CartDTO> orderProductVOList;

}