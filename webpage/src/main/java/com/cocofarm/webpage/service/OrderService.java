package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.mapper.OrderMapper;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    public ArrayList<OrderVO> selectOrderMember(int member_no){
         ArrayList<OrderVO>list = orderMapper.selectOrderMember(member_no);

    return list;
    }
   
}
