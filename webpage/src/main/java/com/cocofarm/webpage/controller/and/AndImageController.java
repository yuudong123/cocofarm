package com.cocofarm.webpage.controller.and;

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
public class AndImageController {

    private static final String IMAGE_DIRECTORY = "D:/cocofarm/webpage/src/main/resources/static/images";

    @Autowired
    ImageService service;

    @GetMapping("image/{filename}") // 안드에서만 씀
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Path imagePath = Paths.get(IMAGE_DIRECTORY).resolve(filename);
        Resource imageResource;
        try {
            imageResource = new UrlResource(imagePath.toUri());
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            String mime;
            switch (ext) {
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
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, mime).body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ResponseBody
    @PostMapping(value = "/selectimagelist.and", produces = "text/html;charset=utf-8")
    public String selectImageList() {
        ArrayList<ImageDTO> list = service.selectImageList();
        return new Gson().toJson(list);
    }

    @ResponseBody
    @PostMapping(value = "/selectproductimagelist.and", produces = "text/html;charset=utf-8")
    public String selectProductImageList(int product_id) {
        ArrayList<ImageDTO> list = service.selectAllImageWithProductId(product_id);
        System.out.println(list);
        return new Gson().toJson(list);
    }
}
