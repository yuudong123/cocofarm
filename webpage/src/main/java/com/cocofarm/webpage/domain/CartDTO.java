package com.cocofarm.webpage.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartDTO implements Serializable {
    private int cart_id;
    private int member_no;
    private int product_id;
    private int amount;

    private String product_name;
    private int product_price;
    private String product_image;

}
