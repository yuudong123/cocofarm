package com.cocofarm.webpage.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartDTO implements Serializable {
    private int cart_id;
    private int member_no;
    private int product_id;
    private int amount;

    private int product_name;
    private int product_price;
    private int product_image;

}
