package com.cocofarm.andapp.order;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OrderVO implements Serializable {
    private String order_id;
    private int member_no;

    @JsonAdapter(DateJsonAdapter.class)
    private Date orderdate;
    private int price;
    private String address;
    private int order_status_cd;

}
