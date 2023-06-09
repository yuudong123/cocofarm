package com.cocofarm.andapp.board;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QnaDTO extends BoardVO {
    private String product_name;
    private String product_content;
}
