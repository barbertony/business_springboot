package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Product;

import java.util.List;

public interface IProductService {
    List<Product> selectAll();
    int updateByPrimaryKey(Product record);
    int deleteByPrimaryKey(Integer id);
    int insert(Product record)throws MyException;
    List<String>subImages(String subImages);
    ServerResponse list_portal(Integer categoryId, String keyword,Integer pageNum,Integer pageSize,String orderBy);
    ServerResponse detail_portal(Integer productId,Integer is_new,Integer is_hot,Integer is_banner);
    ServerResponse list(Integer pageNum,Integer pageSize);
    ServerResponse search(String productName,Integer productId,Integer pageNum,Integer pageSize);
}
