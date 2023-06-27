package com.cocofarm.andapp.order;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OrderProductVO implements Serializable {
    private int orderproduct_id;
    private int product_id;
    private int order_id;
    private int member_no;
    private int amount;
    private int price;

    private int order_status_cd;
    private String filename;
    private String value;

    @JsonAdapter(DateJsonAdapter.class)
    private Date orderdate;
    private String name;
    private int review_board_no;
}
