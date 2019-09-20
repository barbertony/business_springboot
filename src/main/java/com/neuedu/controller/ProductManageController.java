package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product/")
@CrossOrigin
public class ProductManageController {
    @Autowired
    IProductService productService;
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }
        return productService.list(pageNum,pageSize);

    }
    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,
                                 @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                 @RequestParam(required = false)String productName,
                                 @RequestParam(required = false)Integer productId)
    {
        return productService.search(productName,productId,pageNum,pageSize);
    }

}
