package com.cocofarm.andapp.product;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ProductVO implements Serializable {
    private int product_id;
    private String name;
    private String content;
    private int price;
    private int amount;
    private Date regdate;
    private String isdeleted;
}
