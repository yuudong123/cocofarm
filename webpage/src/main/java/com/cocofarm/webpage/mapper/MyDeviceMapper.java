package com.cocofarm.webpage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.MyDeviceVO;

@Mapper
public interface MyDeviceMapper {

    public List<MyDeviceVO> mydevice(int member_no);

    public int add_device(MyDeviceVO vo);
}
