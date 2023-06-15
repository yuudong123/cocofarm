package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;
    // @Autowired
    // ImageMapper imageMapper;

    public ArrayList<ProductVO> selectProductList(int category_cd) {
        ArrayList<ProductVO> list = productMapper.selectProductList(category_cd);
        return list;
    }

    public ProductVO selectProductContent(int product_id) {
        ProductVO productCon = productMapper.selectProductContent(product_id);
        // productCon.setImg(imageMapper.selectAllImageWithProductId(product_id));
        return productCon;
    }

    public ArrayList<ProductVO> selectProductListWithImage() {
        ArrayList<ProductVO> list = productMapper.selectProductListWithImage();
        return list;
    }

    public ArrayList<QnaDTO> selectProductQnaList() {
        ArrayList<QnaDTO> list = productMapper.selectProductQnaList();
        for (QnaDTO qnaDTO : list) {
            ReplyVO answer = productMapper.selectProductQnaAnswer();
            if (answer != null) {
                qnaDTO.setAnswer(answer);
            }
        }
        return list;
    }
}
