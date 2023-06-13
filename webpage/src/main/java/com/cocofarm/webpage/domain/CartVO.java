package com.cocofarm.webpage.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartVO implements Serializable {
    private int cart_id;
    private int member_no;
    private int product_id;
    private int amount;

}
