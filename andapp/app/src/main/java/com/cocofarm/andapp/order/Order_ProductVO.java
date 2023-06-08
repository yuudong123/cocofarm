package com.cocofarm.andapp.order;

import lombok.Data;

@Data
public class Order_ProductVO {
    private int order_product_id;
    private int product_id;
    private int order_id;
    private int member_no;
    private int amount;
    private int price;
}
