package com.cocofarm.andapp.order;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class OrderProductDTO implements Serializable {
    private int order_id;
    private ArrayList<OrderProductVO> orderproduct_list;

    private int member_no;

    @JsonAdapter(DateJsonAdapter.class)
    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;
    private String value;

}
