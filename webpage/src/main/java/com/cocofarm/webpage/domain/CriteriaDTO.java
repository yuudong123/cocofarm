package com.cocofarm.webpage.domain;

import lombok.Data;

@Data
public class CriteriaDTO {
    private int code;
    // private int pagenum;
    // private int board_per_page;
    // private int startnum;
    // private int endnum;
    private String keyword;

    public CriteriaDTO(int code, /* int pagenum, int board_per_page, */ String keyword) {
        this.code = code;
        // this.pagenum = pagenum;
        // this.board_per_page = board_per_page;
        // this.endnum = pagenum * board_per_page;
        // this.startnum = endnum - board_per_page + 1;
        this.keyword = keyword;
    }
}
