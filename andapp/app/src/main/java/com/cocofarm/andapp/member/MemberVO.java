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
    private String phonenumber;
    private String address;
    @JsonAdapter(DateJsonAdapter.class)
    private Date joindate;
    private String isactivated;
    private String sns;
    private String m_question;
    private String m_answer;

    public MemberVO() {
    }

}
