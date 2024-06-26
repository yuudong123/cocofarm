package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ChangeAndRefundDTO;
import com.cocofarm.webpage.domain.OrderDTO;
import com.cocofarm.webpage.domain.OrderProductDTO;
import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;

@Mapper
public interface OrderMapper {

    public int OrderInsert(OrderVO vo);

    public int OrderProductInsert(OrderVO vo);

    public int OrderProductStatusUpdate(OrderProductVO vo);

    public int ChangeAndRefundInsert(ChangeAndRefundDTO dto);

    // public OrderProductVO OrderProductReviewWritePage(int orderproduct_id);
    public BoardVO selectreviewboard(int orderproduct_id);

    // 배송조회용
    public OrderVO selectorder(String order_id, int member_no);

    // 리뷰쓰기용
    public OrderProductVO selectorderproduct(int orderproduct_id, int member_no);

    public ArrayList<OrderProductDTO> MyOrderList(int member_no);

    // public ArrayList<OrderProductVO> OrderProductLowerList(OrderVO vo);

    public ArrayList<OrderDTO> MyOrder(int member_no);

    public ArrayList<OrderProductDTO> MyOrderDetail(String order_id, int member_no);

}
