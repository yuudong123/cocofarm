package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.domain.ReplyVO;
import com.cocofarm.webpage.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public ArrayList<ProductVO> selectProductList(int category_cd) {
        ArrayList<ProductVO> list = productMapper.selectProductList(category_cd);
        return list;
    }

    public ProductVO selectProduct(int product_id) {
        ProductVO vo = productMapper.selectProduct(product_id);
        return vo;
    }

    public ArrayList<ProductVO> selectProductListWithImage() {
        ArrayList<ProductVO> list = productMapper.selectProductListWithImage();
        return list;
    }

    public ProductVO selectProductWithImage(int product_id) {
        ProductVO productvo = productMapper.selectProductWithImage(product_id);
        return productvo;
    }

    public ArrayList<QnaDTO> selectProductQnaList(int product_id, int page) {
        ArrayList<QnaDTO> list = productMapper.selectProductQnaList(product_id, page);
        for (QnaDTO qnaDTO : list) {
            ReplyVO answer = productMapper.selectProductQnaAnswer(qnaDTO.getBoard_no());
            if (answer != null) {
                qnaDTO.setAnswer(answer);
            }
        }
        return list;
    }

    public int selectProductQnaTotal(int product_id, int page) {
        return productMapper.selectProductQnaTotal(product_id, page);
    }

    public ArrayList<BoardVO> selectProductReviewList(int product_id, int page) {
        ArrayList<BoardVO> list = productMapper.selectProductReviewList(product_id, page);
        return list;
    }

    public int selectProductReviewTotal(int product_id, int page) {
        return productMapper.selectProductReviewTotal(product_id, page);
    }
}
