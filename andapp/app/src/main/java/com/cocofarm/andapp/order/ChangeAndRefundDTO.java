package com.cocofarm.andapp.order;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ChangeAndRefundDTO implements Serializable {
    private int changeandrefund_id;
    private int orderproduct_id;
    @JsonAdapter(DateJsonAdapter.class)
    private Date candrdate;
    private int reasoncode;
    private String textreason;

    private int amount;
    private int price;
    private int product_id;
    private int member_no;
    private int order_status_cd;
}
