package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.mapper.ImageMapper;
import com.cocofarm.webpage.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    ImageMapper imageMapper;

 
    public ArrayList<ProductVO> selectProductList(int category_cd) {
        ArrayList<ProductVO> list = productMapper.selectProductList(category_cd);
        return list;
    }
    
    public ProductVO selectProductContent(int product_id){
        ProductVO productCon = productMapper.selectProductContent(product_id);
        productCon.setImg(imageMapper.selectAllImageWithProductId(product_id));
        return productCon;
    }



    // 이미지 한개 프로덕트 여러개 부르는 서비스
    //  public ArrayList<ProductVO> selectProductList() {
    //     ArrayList<ProductVO> list = productMapper.selectProductList();
    //     return list;
    // }

   




}
