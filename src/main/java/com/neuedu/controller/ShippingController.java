package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IShippingService;
import com.neuedu.service.impl.ShippingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
@CrossOrigin
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    @RequestMapping(value = "add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return shippingService.add(userInfo.getId(),shipping);

    }
    @RequestMapping(value = "del.do")
    public ServerResponse del(HttpSession session,Integer shippingId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return shippingService.del(shippingId,userInfo.getId());
    }
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Shipping shipping)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        shipping.setUserId(userInfo.getId());
        return shippingService.update(shipping);

    }
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session,Integer shippingId)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return shippingService.select(userInfo.getId(),shippingId);
    }
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session, @RequestParam(required = false,defaultValue = "1")Integer pageNum, @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        return shippingService.list(userInfo.getId(),pageNum,pageSize);

    }
}
