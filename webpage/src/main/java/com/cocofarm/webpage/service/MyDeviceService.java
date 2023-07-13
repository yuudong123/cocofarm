package com.cocofarm.webpage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.MyDeviceVO;
import com.cocofarm.webpage.mapper.MyDeviceMapper;


@Service
public class MyDeviceService {
    
  @Autowired
  MyDeviceMapper mapper;
  public List<MyDeviceVO> mydevice(int member_no) {
    return mapper.mydevice(member_no);
  }

  public int add_device(MyDeviceVO vo) {
    return mapper.add_device(vo);
  }
}
