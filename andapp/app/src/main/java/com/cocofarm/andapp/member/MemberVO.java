package com.cocofarm.andapp.member;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MemberVO implements Serializable {
    private int member_no;
    private String nickname;
    private int member_type_cd;

    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    @JsonAdapter(DateJsonAdapter.class)
    private Date joindate;
    private String isactivated = "Y";


    public MemberVO(int member_no, String nickname, int member_type_cd) {
        this.member_no = member_no;
        this.nickname = nickname;
        this.member_type_cd = member_type_cd;
    }

    public MemberVO(int member_no, String nickname, int member_type_cd, String email, String password) {
        this.member_no = member_no;
        this.nickname = nickname;
        this.member_type_cd = member_type_cd;
        this.email = email;
        this.password = password;
    }

}
