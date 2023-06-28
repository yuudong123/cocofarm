package com.cocofarm.webpage.controller.and;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.ProductVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.ProductService;
import com.google.gson.Gson;

@Controller
public class AndProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/selectProductList.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductList(int category_cd) {
        ArrayList<ProductVO> productList = productService.selectProductList(category_cd);
        return new Gson().toJson(productList);
    }

    @PostMapping(value = "/selectProductContent.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductConetent(int product_id) {
        ProductVO productContent = productService.selectProductContent(product_id);
        return new Gson().toJson(productContent);
    }

    @PostMapping(value = "/selectproductlistwithimage.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductListWithImage() {
        ArrayList<ProductVO> list = productService.selectProductListWithImage();
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/selectproductqnalist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductQnaListAnd(int product_id, int page) {
        ArrayList<QnaDTO> list = productService.selectProductQnaList(product_id, page);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/selectproductqnatotal.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductQnaTotalAnd(int product_id, int page) {
        return new Gson().toJson(productService.selectProductQnaTotal(product_id, page));
    }

    @PostMapping(value = "/selectproductreviewlist.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductReviewListAnd(int product_id, int page) {
        ArrayList<BoardVO> list = productService.selectProductReviewList(product_id, page);
        return new Gson().toJson(list);
    }

    @PostMapping(value = "/selectproductreviewtotal.and", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String selectProductReviewTotalAnd(int product_id, int page) {
        return new Gson().toJson(productService.selectProductReviewTotal(product_id, page));
    }
}