package com.cocofarm.andapp.board;

import com.cocofarm.andapp.util.DateJsonAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class QnaDTO implements Serializable {
    private int board_no;
    private int member_no;
    private String nickname;
    private int board_category_cd;
    private int product_id;
    private String title;
    private String content;
    @JsonAdapter(DateJsonAdapter.class)
    private Date regdate;
    @JsonAdapter(DateJsonAdapter.class)
    private Date upddate;
    private String mainimage;
    private int replycnt;
    private String product_name;
    private String product_content;
    private ReplyVO answer;
}
