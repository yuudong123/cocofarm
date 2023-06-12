package com.cocofarm.webpage.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.ImageDTO;
import com.cocofarm.webpage.service.ImageService;
import com.google.gson.Gson;

@Controller
public class ImageController {

    private static final String IMAGE_DIRECTORY = "D:/cocofarm/webpage/src/main/resources/static/images";

    @Autowired
    ImageService service;

    @GetMapping("image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(IMAGE_DIRECTORY).resolve(filename);
            Resource imageResource = new UrlResource(imagePath.toUri());
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            String mime;
            switch (extension) {
                case "png":
                    mime = "image/png";
                    break;
                case "jpg":
                case "jpeg":
                    mime = "image/jpeg";
                    break;
                default:
                    mime = "application/octet-stream";
            }
            if (imageResource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, mime) // 이미지 타입에 따라 변경
                        .body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @PostMapping(value = "/selectimagelist.and", produces = "text/html;charset=utf-8")
    public String selectImageList() {
        ArrayList<ImageDTO> list = service.selectImageList();
        return new Gson().toJson(list);
    }

}
