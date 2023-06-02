package com.cocofarm.andapp.product;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
    private int product_id;
    private String name;
    private String content;
    private int price;
    private int amount;
    private Date regdate;
    private String isdeleted;
}
