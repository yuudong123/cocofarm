package com.cocofarm.webpage.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberVO implements Serializable {
    private int member_no;
    private int member_type_cd;
}
