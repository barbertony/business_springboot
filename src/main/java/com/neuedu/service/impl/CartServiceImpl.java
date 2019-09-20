package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {

        if (productId==null||count==null)
        {
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null)
        {   Cart cart1=new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.CartStatusEnum.PRODUCT_CHECKED.getCode());
            cartMapper.insert(cart1);
        }
        else
        {
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(cart.getQuantity()+count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }
        CartVO cartVO=getCartVOLimit(userId);
        return ServerResponse.createServerResponseBySuccess(null,cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO=getCartVOLimit(userId);
        if (cartVO.getCartProductVoList().size()>0)
        {
            return ServerResponse.createServerResponseBySuccess(null,cartVO);
        }
        return ServerResponse.createServerResponseByFail(1,"还没有选中任何商品哦~");
    }
    @Override
    public ServerResponse list_checked(Integer userId) {
        CartVO cartVO=getCartVOLimit_select(userId);
        if (cartVO.getCartProductVoList().size()>0)
        {
            return ServerResponse.createServerResponseBySuccess(null,cartVO);
        }
        return ServerResponse.createServerResponseByFail(1,"还没有选中任何商品哦~");
    }


    @Override
    public ServerResponse update(Integer userId,Integer productId, Integer count) {
        if (productId==null||count==null)
        {
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart!=null)
        {
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(cart.getChecked());
            int result=cartMapper.updateByPrimaryKey(cart1);
            if (result<1)
            {
                return ServerResponse.createServerResponseByFail(2,"更新数据失败");
            }
        }
        else
        {
            return ServerResponse.createServerResponseByFail(2,"更新数据失败");
        }

        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        if (productIds==null)
        {
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        String productIdStr[]=productIds.split(",");
        for (String str:productIdStr) {
            Integer productId=Integer.parseInt(str);
            Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
            if (cart==null)
            {
                return ServerResponse.createServerResponseByFail(3,"商品不存在");
            }
            cartMapper.deleteByPrimaryKey(cart.getId());
        }


        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse select(Integer userId, Integer productId) {
        if (productId==null)
        {
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null)
        {
            return ServerResponse.createServerResponseByFail(3,"商品不存在");
        }
        cart.setChecked(1);
        cartMapper.updateByPrimaryKey(cart);
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse un_select(Integer userId, Integer productId) {
        if (productId==null)
        {
            return ServerResponse.createServerResponseByFail(9,"参数不能为空");
        }
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null)
        {
            return ServerResponse.createServerResponseByFail(3,"商品不存在");
        }
        cart.setChecked(0);
        cartMapper.updateByPrimaryKey(cart);
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        if (userId==null)
        {
            return ServerResponse.createServerResponseByFail(10, "出现异常");
        }
        int result=cartMapper.get_cart_product_count(userId);


        return ServerResponse.createServerResponseBySuccess(null,result);
    }

    @Override
    public ServerResponse select_all(Integer userId) {
        cartMapper.select_all(userId);


        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse un_select_all(Integer userId) {
        cartMapper.un_select_all(userId);


        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    private CartVO getCartVOLimit(Integer userId)

    {
        CartVO cartVO=new CartVO();
        //1.
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        //2
        List<CartProductVO> cartProductVOList= Lists.newArrayList();

        BigDecimal cartTotalPrice=new BigDecimal("0");


        if (cartList!=null&&cartList.size()>0)
        {
            for (Cart c:cartList) {
                CartProductVO cartProductVO=new CartProductVO();
                cartProductVO.setId(c.getId());
                cartProductVO.setQuantity(c.getQuantity());
                cartProductVO.setUserId(c.getUserId());
                cartProductVO.setProductChecked(c.getChecked());

                Product product=productMapper.selectByPrimaryKey(c.getProductId());
                if (product!=null)
                {
                    cartProductVO.setProductId(product.getId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    int stock=product.getStock();
                    int limitProductCount=0;
                    if (stock>c.getQuantity())
                    {
                        limitProductCount=c.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");


                    }
                    else
                    {
                        Cart cart=new Cart();
                        cart.setQuantity(stock);
                        cart.setId(c.getId());
                        cart.setProductId(c.getProductId());
                        cart.setChecked(c.getChecked());
                        cart.setUserId(c.getUserId());
                        cartMapper.updateByPrimaryKey(cart);
                        cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");

                    }
                    cartProductVO.setQuantity(limitProductCount);
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cartProductVO.getQuantity()));



                }
                if (c.getChecked()==1){cartTotalPrice= BigDecimalUtils.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());}

                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVoList(cartProductVOList);

        int count =cartMapper.isCheckedAll(userId);
        if (count>0)
        {
            cartVO.setAllChecked(false);
        }
        else
        {
            cartVO.setAllChecked(true);
        }


        return cartVO;
    }

    private CartVO getCartVOLimit_select(Integer userId)

    {
        CartVO cartVO=new CartVO();
        //1.
        List<Cart> cartList=cartMapper.selectCartByUserIdAndChecked(userId);
        //2
        List<CartProductVO> cartProductVOList= Lists.newArrayList();

        BigDecimal cartTotalPrice=new BigDecimal("0");


        if (cartList!=null&&cartList.size()>0)
        {
            for (Cart c:cartList) {
                CartProductVO cartProductVO=new CartProductVO();
                cartProductVO.setId(c.getId());
                cartProductVO.setQuantity(c.getQuantity());
                cartProductVO.setUserId(c.getUserId());
                cartProductVO.setProductChecked(c.getChecked());

                Product product=productMapper.selectByPrimaryKey(c.getProductId());
                if (product!=null)
                {
                    cartProductVO.setProductId(product.getId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    int stock=product.getStock();
                    int limitProductCount=0;
                    if (stock>c.getQuantity())
                    {
                        limitProductCount=c.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");


                    }
                    else
                    {
                        Cart cart=new Cart();
                        cart.setQuantity(stock);
                        cart.setId(c.getId());
                        cart.setProductId(c.getProductId());
                        cart.setChecked(c.getChecked());
                        cart.setUserId(c.getUserId());
                        cartMapper.updateByPrimaryKey(cart);
                        cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");

                    }
                    cartProductVO.setQuantity(limitProductCount);
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cartProductVO.getQuantity()));



                }
                if (c.getChecked()==1){cartTotalPrice= BigDecimalUtils.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());}

                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVoList(cartProductVOList);

        int count =cartMapper.isCheckedAll(userId);
        if (count>0)
        {
            cartVO.setAllChecked(false);
        }
        else
        {
            cartVO.setAllChecked(true);
        }


        return cartVO;
    }
}
