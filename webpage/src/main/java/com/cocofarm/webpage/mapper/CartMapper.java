package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.CartDTO;

@Mapper
public interface CartMapper {

    public ArrayList<CartDTO> selectCartList(int member_no);

    public int insert(CartDTO dto);
       

    
}
