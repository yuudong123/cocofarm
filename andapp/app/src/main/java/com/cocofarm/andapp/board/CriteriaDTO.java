package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;

import java.io.Serializable;

import lombok.Data;

@Data
public class CriteriaDTO implements Serializable {
    private int code;
    private int pagenum;
    private int board_per_page;
    private int startnum;
    private int endnum;
    private String keyword;

    public CriteriaDTO() {
        this.code = BOARD_CATEGORY_NOTICE;
        this.pagenum = 1;
        this.board_per_page = 10;
        this.endnum = pagenum * 10;
        this.startnum = endnum - 9;
        this.keyword = "";
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
        this.endnum = pagenum * 10;
        this.startnum = endnum - 9;
    }
}
