package com.cocofarm.webpage.domain;

import static com.cocofarm.webpage.common.CodeTable.BOARD_CATEGORY_NOTICE;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriteriaDTO {
    private int code = BOARD_CATEGORY_NOTICE;
    private int productId;
    private int page = 1;
    private int boardPerPage = 10;
    private String keyword = "";

    public CriteriaDTO(int code, String keyword) {
        this.code = code;
        this.keyword = keyword;
    }

    public CriteriaDTO() {
    }
}
