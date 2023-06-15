package com.cocofarm.webpage.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QnaDTO extends BoardVO {
    private String product_name;
    private String product_content;
    private ReplyVO answer;
}
