package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.mapper.CartMapper;

@Service
public class CartService {
    
    @Autowired
    CartMapper cartmapper;

    public ArrayList<CartDTO> selectCartList(int member_no){
        ArrayList<CartDTO> list = cartmapper.selectCartList(member_no);
        return list;
    }

    public int insert(CartDTO dto){
        return cartmapper.insert(dto);
    }

    public int deleteCartProductOne(CartDTO dto){
        return cartmapper.deleteCartProductOne(dto);
    }

    public int deleteCartList(int member_no){
        return cartmapper.deleteCartList(member_no);
    }

    
}
