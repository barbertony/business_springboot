package com.neuedu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.ConfigurationSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/order/")
@CrossOrigin
public class OrderController {
    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "create.do")
    public ServerResponse createOrder(HttpSession session,Integer shippingId)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");
        }
        return orderService.createOrder(userInfo.getId(),shippingId);
    }



    @RequestMapping(value = "cancel.do")
    public ServerResponse cancel(HttpSession session,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }

        return orderService.cancel(userInfo.getId(),orderNo);
    }

    @RequestMapping(value = "get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }

        return orderService.get_order_cart_product(userInfo.getId());

    }



    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session, @RequestParam(required = false,defaultValue = "1")Integer pageNum, @RequestParam(required = false,defaultValue = "10")Integer pageSize)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }

        return orderService.list(userInfo.getId(),pageSize,pageNum);
    }
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session ,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        return orderService.detail(userInfo.getId(),orderNo);

    }



    /*支付接口*/
    @RequestMapping(value = "pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        return orderService.pay(userInfo.getId(),orderNo);
    }



    @RequestMapping(value = "alipay_callback.do")
    public String alipay_callback(HttpServletRequest request)
    {
        System.out.println("===============diaoyomhchengg===========");




        //验证签名
        Map<String,String[]> params=request.getParameterMap();
        Map<String,String> requestparams= Maps.newHashMap();
        Iterator<String> it=params.keySet().iterator();
        while (it.hasNext())
        {
            String key=it.next();
            String [] strArr=params.get(key);
            String value="";
            for (int i=0;i<strArr.length;i++)
            {
                value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparams.put(key,value);
        }

        try {
            requestparams.remove("sign_type");
            boolean result=AlipaySignature.rsaCheckV2(requestparams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (!result)
            {
                return "fail";

            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        ServerResponse serverResponse=orderService.alipay_callback(requestparams);
        if (serverResponse.isScuess())
        {
            return "success";
        }


        return "fail";
    }

    @RequestMapping(value = "query_order_pay_status.do")
    public ServerResponse query_order_pay_status (HttpSession session,Long orderNo)
    {
        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");

        }
        return orderService.query_order_pay_status(userInfo.getId(),orderNo);
    }
}
