package com.cocofarm.webpage.controller.and;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.domain.MyDeviceVO;
import com.cocofarm.webpage.service.MyDeviceService;
import com.google.gson.Gson;

@RestController
public class AndMyDeviceController {

    @Autowired
    MyDeviceService service;

    @PostMapping(value = "/device/mydevice.and")
    public String mydevice(int member_no) {
        return new Gson().toJson(service.mydevice(member_no));
    }

    @PostMapping(value = "/device/add_device.and")
    public int add_device(String vo) {
        return service.add_device(new Gson().fromJson(vo, MyDeviceVO.class));
    }

    @PostMapping(value = "/device/cnt_device.and")
    public int cnt_device(int member_no) {
        return service.cnt_device(member_no);
    }

    @PostMapping(value = "/device/dvname_modify.and")
    public void dvname_modify(int member_no, String mydevice_name, int mydevice_id) {
        System.out.println(mydevice_name + " " +mydevice_id );
        service.dvname_modify(mydevice_name, mydevice_id);
        service.mydevice(member_no);
    }

}
