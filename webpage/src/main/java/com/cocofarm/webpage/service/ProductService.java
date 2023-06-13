package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    ProductMapper ProductMapper;

    public ArrayList<ProductVO> selectProductPlant() {
        ArrayList<ProductVO> list = ProductMapper.selectProductPlant();
        return list;
    }

    public ArrayList<ProductVO> selectProductDevice() {
        ArrayList<ProductVO> list = ProductMapper.selectProductDevice();
        return list;
    }

}
