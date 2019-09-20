package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
@CrossOrigin
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;
    @RequestMapping(value = "list.do")
    public ServerResponse list(@RequestParam(required = false,defaultValue = "0")Integer categoryId,@RequestParam(required = false,defaultValue = "")String keyword,  @RequestParam(required = false,defaultValue = "1")Integer pageNum, @RequestParam(required = false,defaultValue = "10")Integer pageSize, @RequestParam(required = false,defaultValue = "")String orderBy)
    {
        return productService.list_portal(categoryId,keyword,pageNum,pageSize,orderBy);
    }

    @RequestMapping(value = "detail.do")
    public ServerResponse detail(Integer productId,Integer is_new,Integer is_hot,Integer is_banner)
    {
        return productService.detail_portal(productId,is_new,is_hot,is_banner);
    }
    @RequestMapping(value = "topcategory.do")
    public ServerResponse topcategory(@RequestParam(required = false,defaultValue = "0") Integer sid)
    {
        return categoryService.get_category(sid);
    }
    @RequestMapping(value = "logempty.do")
    public ServerResponse logempty()
    {
        return ServerResponse.createServerResponseBySuccess("调用成功");
    }

}
