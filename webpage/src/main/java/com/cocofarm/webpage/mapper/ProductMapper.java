package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.domain.ReplyVO;

@Mapper
public interface ProductMapper {

    public ProductVO selectProduct(int product_id); // productVO하나만 불러올때

    public ArrayList<ProductVO> selectProductList(int category_cd); // 카테고리로 분류해서 상품전체 검색, 이미지는 처음것만 검색

    public ArrayList<ProductVO> selectProductListWithImage(); // 현재's : 이미지만 하나씩+ 상품 다 불러오기

    public ProductVO selectProductWithImage(int product_id);

    public ArrayList<QnaDTO> selectProductQnaList(int product_id, int page);

    public ReplyVO selectProductQnaAnswer(int board_no);

    public int selectProductQnaTotal(int product_id, int page);

    public ArrayList<BoardVO> selectProductReviewList(int product_id, int page);

    public int selectProductReviewTotal(int product_id, int page);

}
