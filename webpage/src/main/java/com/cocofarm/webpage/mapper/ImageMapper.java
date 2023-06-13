package com.cocofarm.webpage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.cocofarm.webpage.domain.ImageDTO;

@Mapper
public interface ImageMapper {
    public ArrayList<ImageDTO> selectImageList();

    public ArrayList<ImageDTO> selectProductImageList();
}
