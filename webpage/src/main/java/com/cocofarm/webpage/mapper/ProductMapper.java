package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ProductVO;

@Mapper
public interface ProductMapper {

    public ProductVO selectProductContent(int product_id); // 나중에 파라미터 두개 추가 프로덕트 아이디, +a 로 카테고리.

    public ArrayList<ProductVO> selectProductList(int category_cd); // 이미지 전체 부르기.

    public ArrayList<ProductVO> selectProductListWithImage(); // 이미지 하나 프로덕트 전체 부르기.

}
