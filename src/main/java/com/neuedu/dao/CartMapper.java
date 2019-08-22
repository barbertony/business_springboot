package com.neuedu.dao;

import com.neuedu.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    List<Cart> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);

    List<Cart> selectCartByUserId(@Param("userId") Integer userId);

    int isCheckedAll(@Param("userId") Integer userId);

    int get_cart_product_count(@Param("userId") Integer userId);

    int select_all(@Param("userId") Integer userId);
    int un_select_all(@Param("userId") Integer userId);

    List<Cart> findCartListByUserIdAndChecked(@Param("userId") Integer userId);

    int bathDelete(List<Cart> cartList);
}