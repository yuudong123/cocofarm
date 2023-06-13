package com.cocofarm.webpage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.mapper.ImageMapper;

@Service
public class ImageService {

    @Autowired
    ImageMapper mapper;

    public ArrayList<ImageDTO> selectImageList() {
        ArrayList<ImageDTO> list = mapper.selectImageList();
        return list;
    }

    public ArrayList<ImageDTO> selectProductImageList() {
        ArrayList<ImageDTO> list = mapper.selectProductImageList();
        return list;
    }
}
