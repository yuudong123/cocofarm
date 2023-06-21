package com.cocofarm.webpage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocofarm.webpage.domain.OrderProductVO;
import com.cocofarm.webpage.domain.OrderVO;
import com.cocofarm.webpage.mapper.OrderMapper;

@Service
public class OrderService {

    @Autowired
    OrderMapper ordermapper;

    @Transactional
    public int OrderInsert(OrderVO vo) {
        vo.setOrderdate(new Date());
        vo.setOrder_id(makeOrderId(vo));
        int orderresult = ordermapper.OrderInsert(vo);
        int orderProresult = ordermapper.OrderProductInsert(vo);
        return orderresult+orderProresult;
    }

    private String makeOrderId(OrderVO vo) {
        // 주문번호(문자열) 만드는 메소드
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(vo.getOrderdate());
        String member_no = vo.getMember_no() + "";
        String random = generateRandomNumber(1000, 9999) + "";
        String result = sDate + member_no + random;
        return result;
    }

    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
