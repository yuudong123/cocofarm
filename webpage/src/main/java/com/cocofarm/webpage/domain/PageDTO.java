package com.cocofarm.webpage.domain;

import lombok.Data;

@Data
public class PageDTO {
    private int startPage;
    private int endPage;
    private int lastPage;
    private int total;
    private CriteriaDTO cri;

    public PageDTO(CriteriaDTO cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPage() / 10.0)) * 10;
        this.startPage = this.endPage - 9;
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getBoardPerPage()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }
        this.lastPage = (total - 1) / cri.getBoardPerPage() + 1;
    }
}
