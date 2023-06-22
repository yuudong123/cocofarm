package com.cocofarm.webpage.mapper;


import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;

@Mapper
public interface OrderMapper {

    public int OrderInsert(OrderVO vo);

    public int OrderProductInsert(OrderVO vo);

    public ArrayList<OrderProductVO> OrderProductList(OrderVO vo);

    public int OrderProductStatusUpdate(OrderProductVO vo);
    
}
