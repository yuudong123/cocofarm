package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ProductVO;

@Mapper
public interface ProductMapper {

    public ProductVO selectProductContent(int product_id); // productVO하나만 불러올때 

    public ArrayList<ProductVO> selectProductList(int category_cd); // 카테고리로 분류해서 상품전체 검색, 이미지는 처음것만 검색

    public ArrayList<ProductVO> selectProductListWithImage(); // 현재's : 이미지만 하나씩+ 상품 다 불러오기

}
