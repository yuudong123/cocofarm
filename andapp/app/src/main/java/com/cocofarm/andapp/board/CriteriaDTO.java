package com.cocofarm.andapp.board;

import java.io.Serializable;

import lombok.Data;

@Data
public class CriteriaDTO implements Serializable {
    private int code;
    private int page;
    private int boardPerPage;
    private String keyword;
}
