package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ProductVO;

@Mapper
public interface ProductMapper {
    public ArrayList<ProductVO> selectProductPlant();

    // public ArrayList<BoardVO> selectList(int code);
    public ArrayList<ProductVO> selectProductDevice();

}
