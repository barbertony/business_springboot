package com.neuedu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;


    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        if (shipping==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySuccess(null,map);
    }

    @Override
    public ServerResponse del(Integer shippingId,Integer userId) {
        if (shippingId==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        int result=shippingMapper.deleteByPrimaryKeyAndUserId(shippingId,userId);
        if (result==1)
        {
            return ServerResponse.createServerResponseBySuccess("删除地址成功");
        }
        return ServerResponse.createServerResponseByFail(1,"删除地址失败");
    }

    @Override
    public ServerResponse update( Shipping shipping) {
        if (shipping==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        int result=shippingMapper.updateSelectiveKeyByPrimaryKeyAndUserId(shipping);
        if (result==1)
        {
            return ServerResponse.createServerResponseBySuccess("更新地址成功");
        }
        return ServerResponse.createServerResponseByFail(1,"更新地址失败");
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
        if (shippingId==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        Shipping shipping=shippingMapper.selectByPrimaryKeyAndUserId(shippingId,userId);
        if (shipping==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }

        return ServerResponse.createServerResponseBySuccess(null,shipping);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectAllByUserId(userId);
        PageInfo pageInfo=new PageInfo(page);

        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }


}
