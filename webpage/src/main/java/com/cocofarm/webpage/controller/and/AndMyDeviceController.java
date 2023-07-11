package com.cocofarm.webpage.controller.and;

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
}
