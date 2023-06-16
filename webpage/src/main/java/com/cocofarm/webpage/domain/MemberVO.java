package com.cocofarm.webpage.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MemberVO implements Serializable {
    private int member_no;
    private int member_type_cd;
 
    private String nickname;
    private String email;
    private String password;
    private String phonenumber;
    private String address;
    private Date joindate;
    private String isactivated;
    private String sns;
}
