package com.cocofarm.andapp.product;

import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class ProductVO implements Serializable {
    private int product_id;
    private String name;
    private String content;
    private int price;
    private int amount;

    @JsonAdapter(DateJsonAdapter.class)
    private Date regdate;
    private String isdeleted;

    private int product_category_cd;
    private String filename;
    private ArrayList<ImageDTO> img;

}
