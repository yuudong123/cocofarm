package com.cocofarm.andapp.order;

import lombok.Data;

@Data
public class CartVO {

    private int cart_id;
    private int member_no;
    private int product_id;
    private int amount;
}
