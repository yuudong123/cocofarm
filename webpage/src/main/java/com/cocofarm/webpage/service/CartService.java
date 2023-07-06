package com.cocofarm.webpage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.CartDTO;
import com.cocofarm.webpage.mapper.CartMapper;

@Service
public class CartService {

    @Autowired
    CartMapper cartmapper;

    public ArrayList<CartDTO> selectCartList(int member_no) {
        ArrayList<CartDTO> list = cartmapper.selectCartList(member_no);
        return list;
    }

    // 웹용 장바구니 오더 보이게 하기?
    public ArrayList<CartDTO> selectCartList(List<String> order) {
        ArrayList<CartDTO> list = cartmapper.selectCartOrderList(order);
        return list;
    }

    public int insert(CartDTO dto) {
        return cartmapper.insert(dto);
    }

    public int deleteCartProductOne(CartDTO dto) {
        return cartmapper.deleteCartProductOne(dto);
    }

    public int deleteCartList(int member_no) {
        return cartmapper.deleteCartList(member_no);
    }

    public void deleteCartProducts(List<CartDTO> list) {
        System.out.println(list.size());
        for (CartDTO cartDTO : list) {
            deleteCartProductOne(cartDTO);
        }
    }

}
