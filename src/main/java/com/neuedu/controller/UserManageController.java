package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user/")
@CrossOrigin
public class UserManageController {
    @Autowired
    IUserService userService;
    @Autowired
    UserInfo sessionUserInfo;
    @RequestMapping(value = "/login.do")
    public ServerResponse login(UserInfo userInfo, HttpServletRequest request)
    {
        if (userInfo.getPassword()!=null&&!userInfo.getPassword().equals(""))
        {
            userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        }


        ServerResponse serverResponse= userService.login(userInfo);
        if (serverResponse.isScuess())
        {
            UserInfo userInfo1=(UserInfo) serverResponse.getData();
            if (userInfo1.getRole()!=0)
            {
                return ServerResponse.createServerResponseByFail(1,"没有权限");
            }
            request.getSession().setAttribute(Const.CURRENT_USER,userInfo1);
        }
        return serverResponse;
    }

    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageSize",required = false,defaultValue = "10")String pageSize, @RequestParam(value = "pageNum",required = false,defaultValue = "1")String pageNum)
    {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
        }
        if (userInfo.getRole()!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"没有权限");
        }
        return userService.list(pageSize,pageNum);
    }

}
