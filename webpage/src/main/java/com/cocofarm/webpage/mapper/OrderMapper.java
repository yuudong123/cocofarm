package com.cocofarm.webpage.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.OrderVO;

@Mapper
public interface OrderMapper {

    public int OrderInsert(OrderVO vo);

    public int OrderProductInsert(OrderVO vo);
    
}
