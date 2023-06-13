package com.cocofarm.andapp.board;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriteriaDTO implements Serializable {
    private int code;
    private int page;
    private int boardPerPage;
    private String keyword;

    public CriteriaDTO() {
    }
}
