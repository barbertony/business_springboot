package com.neuedu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProductListBackendVO;
import com.neuedu.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService categoryService;
    @Override
    public List<Product> selectAll() {
        List<Product> productList=productMapper.selectAll();
        for (Product p:productList
        ) {
            System.out.println(p.getCreateTime().toString());
        }
        return productMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Product record) {
        if(record.getName()==null||record.getName().equals(""))
        {
            throw new MyException("商品名不能为空","");
        }
        if(record.getPrice()==null||record.getPrice().equals(""))
        {
            throw new MyException("商品名价格不能为空","");
        }
        if(record.getStock()==null||record.getStock().equals(""))
        {
            record.setStock(0);
        }
        int result=productMapper.exsitsProductname(record.getName());
        Product product=productMapper.selectByPrimaryKey(record.getId());
        if (result>0&&!product.getName().equals(record.getName()))
        {
            throw new MyException("商品名已存在","");
        }
        System.out.println("service==================="+record.getCategoryId());

        int r= productMapper.updateByPrimaryKey(record);
        System.out.println(r);
        return r;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Product record)throws MyException {
        if(record.getName()==null||record.getName().equals(""))
        {
            throw new MyException("商品名不能为空","");
        }
        if(record.getPrice()==null||record.getPrice().equals(""))
        {
            throw new MyException("商品名价格不能为空","");
        }
        if(record.getStock()==null||record.getStock().equals(""))
        {
            record.setStock(0);
        }
        int result=productMapper.exsitsProductname(record.getName());
        if (result>0)
        {
            throw new MyException("商品名已存在","");
        }
        return productMapper.insert(record);
    }

    @Override
    public List<String> subImages(String subImages) {
        List<String> sunImageslist=new ArrayList<>();
        String str=subImages;
        String[] strArr = str.split("\\,");
        for (String s:strArr
        ) {
            sunImageslist.add(s);
            System.out.println(s);
        }
        return sunImageslist;
    }

    @Override
    public ServerResponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        Page page=new Page();
        if (categoryId==null&&(keyword==null||keyword.equals("")))
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        Set<Integer> integerSet= Sets.newHashSet();
        if (categoryId!=null)
        {
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&(keyword==null||keyword.equals(""))&&categoryId!=0)
            {
                page= PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO> productList= Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(page);

                return ServerResponse.createServerResponseBySuccess(pageInfo);
            }
            ServerResponse serverResponse= categoryService.get_deep_category(categoryId);

            if (serverResponse.isScuess())
            {
                integerSet=(Set<Integer>) serverResponse.getData();

            }
        }
        if (keyword!=null||!keyword.equals(""))
        {
            keyword="%"+keyword+"%";
        }




        if(orderBy.equals(""))
        {
            page=PageHelper.startPage(pageNum,pageSize);
        }
        else
        {
            String[]orderByArr=orderBy.split("_");
            if (orderByArr.length>1)
            {
                orderBy=orderByArr[0]+" "+orderByArr[1];
                page=PageHelper.startPage(pageNum,pageSize,orderBy);
                System.out.println(orderBy);
            }
            else{
                page=PageHelper.startPage(pageNum,pageSize);
            }

        }

        List <Product> productList=productMapper.searchProduct(integerSet,keyword,orderBy);
        List<ProductListVO> productListVOList=Lists.newArrayList();

        if (productList!=null&&productList.size()>0)
        {
            for (Product p:productList
            ) {
                ProductListVO productListVO=new ProductListVO();
                productListVO.assembleProductDetailVO(p);
                productListVOList.add(productListVO);
            }
        }

        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(productListVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse detail_portal(Integer productId, Integer is_new, Integer is_hot, Integer is_banner) {

        if (productId==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        Product product =productMapper.selectByPrimaryKey(productId);
        if (product==null)
        {
            return ServerResponse.createServerResponseByFail(1,"参数错误");
        }
        if(product.getStatus()!= Const.ProductStatusEnum.PRODUCT_ONLINE.getCode())
        {
            return ServerResponse.createServerResponseByFail(4,"该商品已下架");
        }
        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.assembleProductDetailVO(product);
        return ServerResponse.createServerResponseBySuccess(null,productDetailVO);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<Product> productList=productMapper.selectAll();
        List<ProductListBackendVO> productListBackendVOArrayList=Lists.newArrayList();
        if (productList!=null&&productList.size()>0)
        {
            for (Product product:productList) {
                ProductListBackendVO productListBackendVO=new ProductListBackendVO();
                productListBackendVO.assembleProductDetailVO(product);
                productListBackendVOArrayList.add(productListBackendVO);

            }
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(productListBackendVOArrayList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        Page page= PageHelper.startPage(pageNum,pageSize);
        if (productName!=null&&!productName.equals(""))
        {
            productName="%"+productName+"%";
            System.out.println(productName);
        }
        List<Product> productList=productMapper.searchByIdAndProductName(productName,productId);
        List<ProductListBackendVO> productListBackendVOArrayList=Lists.newArrayList();
        if (productList!=null&&productList.size()>0)
        {
            for (Product product:productList) {
                ProductListBackendVO productListBackendVO=new ProductListBackendVO();
                productListBackendVO.assembleProductDetailVO(product);
                productListBackendVOArrayList.add(productListBackendVO);

            }
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(productListBackendVOArrayList);

        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

}
