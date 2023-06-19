package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.OrderVO;

@Mapper
public interface OrderMapper {
    public ArrayList<OrderVO> selectOrderMember(int member_no);


    
}
