package com.cocofarm.andapp.member;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberVO implements Serializable {
    private int member_no;
    private String nickname;
    private int member_type_cd;

    public MemberVO(int member_no, String nickname, int member_type_cd) {
        this.member_no = member_no;
        this.nickname = nickname;
        this.member_type_cd = member_type_cd;
    }
}
