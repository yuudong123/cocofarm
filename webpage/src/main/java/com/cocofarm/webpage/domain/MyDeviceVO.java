package com.cocofarm.webpage.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MyDeviceVO {
    private int mydevice_id;
    private int member_no;
    private int product_id;
    private String mydevice_name;
    private Date regdate;
}
