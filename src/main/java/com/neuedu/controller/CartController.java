package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.list(userInfo.getId());
    }
    @RequestMapping(value ="add.do" )
    public ServerResponse add(int productId,int count,HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.add(userInfo.getId(),productId,count);
    }
    @RequestMapping(value = "update.do")
    public ServerResponse update(Integer productId,
                                 Integer count,HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.update(userInfo.getId(),productId,count);
    }
    @RequestMapping(value = "delete_product.do")
    public ServerResponse delete_product(String productIds,
                                         HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.delete_product(userInfo.getId(),productIds);
    }
    @RequestMapping(value ="select.do" )
    public ServerResponse select(Integer productId,HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.select(userInfo.getId(),productId);

    }
    @RequestMapping(value ="un_select.do" )
    public ServerResponse un_select(Integer productId,HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.un_select(userInfo.getId(),productId);

    }
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseBySuccess(null, 0);
        }
        return cartService.get_cart_product_count(userInfo.getId());
    }
    @RequestMapping(value = "select_all.do")
    public ServerResponse select_all(HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.select_all(userInfo.getId());
    }
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse un_select_all(HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return cartService.un_select_all(userInfo.getId());
    }
}
