package com.cocofarm.webpage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriteriaDTO {
    private int code;
    private int page;
    private int boardPerPage;
    private String keyword;

    public CriteriaDTO(int code, String keyword) {
        this.code = code;
        this.keyword = keyword;
    }

    public CriteriaDTO() {

    }
}
