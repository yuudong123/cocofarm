package com.cocofarm.andapp.order;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

import lombok.Data;

@Data
public class ChangeAndRefundDTO {
    private int changeandrefund_id;
    private int orderproduct_id;
    @JsonAdapter(DateJsonAdapter.class)
    private Date candrdate;
    private int reasoncode;
    private String textreason;

    private int amount;
    private int price;
    private int product_id;
}
