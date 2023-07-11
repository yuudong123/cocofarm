package com.cocofarm.webpage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.MyDeviceVO;

@Mapper
public interface MyDeviceMapper {

    public MyDeviceVO mydevice(int member_no);

    public int add_device(MyDeviceVO vo);
}
