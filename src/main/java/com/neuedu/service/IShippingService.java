package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse del(Integer shippingId,Integer userId);
    ServerResponse update(Shipping shipping);
    ServerResponse select(Integer userId, Integer shippingId);
    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);
}
