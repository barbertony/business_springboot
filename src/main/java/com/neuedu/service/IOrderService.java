package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IOrderService {

    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse cancel(Integer userId,Long orderNo);
    ServerResponse get_order_cart_product(Integer userId);
    ServerResponse list(Integer userId,Integer pageSize, Integer pageNum);
    ServerResponse detail(Integer userId,Long orderNo);
    ServerResponse search(Long orderNo,Integer pageNum,Integer pageSize);
    ServerResponse send_goods(Long orderNo);
    ServerResponse pay(Integer userId,Long orderNo);
    ServerResponse alipay_callback(Map<String,String> map);
    ServerResponse query_order_pay_status(Integer userId,Long orderNo);
    public List<Order>  closeOrder(String closeTime);
}
