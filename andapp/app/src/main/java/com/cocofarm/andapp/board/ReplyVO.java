package com.cocofarm.andapp.board;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO implements Serializable {
    private int reply_no;
    private int member_no;
    private String nickname;
    private int board_no;
    private String content;
    @JsonAdapter(DateJsonAdapter.class)
    private Date regdate;
    @JsonAdapter(DateJsonAdapter.class)
    private Date upddate;

    public ReplyVO(int member_no, String nickname, int board_no, String content) {
        this.member_no = member_no;
        this.nickname = nickname;
        this.board_no = board_no;
        this.content = content;
    }

    public ReplyVO() {
    }
}
