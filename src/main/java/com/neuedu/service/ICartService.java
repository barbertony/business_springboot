package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICartService {
    ServerResponse add(Integer userId,Integer productId, Integer count);
    ServerResponse list(Integer userId);
    ServerResponse update(Integer userId,Integer productId, Integer count);
    ServerResponse delete_product(Integer userId,String productIds);
    ServerResponse select(Integer userId,Integer productId);
    ServerResponse un_select(Integer userId,Integer productId);
    ServerResponse get_cart_product_count(Integer userId);
    ServerResponse select_all(Integer userId);
    ServerResponse un_select_all(Integer userId);
}
