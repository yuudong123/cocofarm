package com.cocofarm.webpage.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.CartDTO;

@Mapper
public interface CartMapper {

    public ArrayList<CartDTO> selectCartList(int member_no);

    public ArrayList<CartDTO> selectCartOrderList(List<String> order);

    public int insert(CartDTO dto);

    public int deleteCartProductOne(CartDTO dto);

    public int deleteCartList(int member_no);

}
