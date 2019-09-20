package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
@CrossOrigin
public class CategoryManageController {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    Category sessionCategory;
    @RequestMapping(value = "get_category.do")
    public ServerResponse get_category(HttpServletRequest request,@RequestParam(required = false,defaultValue = "0")int categoryId)
    {
//        UserInfo userInfo=(UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
//        if (userInfo==null)
//        {
//            return ServerResponse.createServerResponseByFail(1,"用户未登录，无法获取当前用户信息");
//        }
        return categoryService.get_category(categoryId);

    }
    @RequestMapping(value = "add_category.do")
    public ServerResponse add_category(@RequestParam(required = false,defaultValue = "0") int parentId  , String categoryName)
    {
        return categoryService.add_category(parentId,categoryName);
    }


    @RequestMapping(value = "set_category_name.do")
    public ServerResponse set_category_name(@RequestParam(required = false,defaultValue = "0")int categoryId,String categoryName)
    {
        sessionCategory.setId(categoryId);
        sessionCategory.setName(categoryName);
        sessionCategory.setStatus(1);
        sessionCategory.setSortOrder(11);
        return categoryService.updateCategory(sessionCategory);
    }

    @RequestMapping(value = "get_deep_category.do")
    public ServerResponse get_deep_category(@RequestParam(required = false,defaultValue = "0")int categoryId, HttpSession session)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null||userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"无权限");
        }

        return categoryService.get_deep_category(categoryId);
    }


}
