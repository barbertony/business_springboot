package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/order/")
@CrossOrigin
public class OrderManageController {
    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session, @RequestParam(required = false,defaultValue = "1")Integer pageNum, @RequestParam(required = false,defaultValue = "10")Integer pageSize)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }

        return orderService.list(null,pageSize,pageNum);
    }
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session ,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }
        return orderService.detail(null,orderNo);

    }

    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session ,Long orderNo,@RequestParam(required = false,defaultValue = "1")Integer pageNum, @RequestParam(required = false,defaultValue = "10")Integer pageSize)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }


        return orderService.search(orderNo,pageNum,pageSize);
    }


    @RequestMapping(value = "send_goods.do")
    public ServerResponse send_goods(HttpSession session ,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }
        return orderService.send_goods(orderNo);
    }
}
