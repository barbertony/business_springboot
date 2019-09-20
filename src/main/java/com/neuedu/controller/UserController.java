package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.vo.UserInfoInformationVO;
import com.neuedu.vo.UserInfoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
@CrossOrigin
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    UserInfo sessionUserInfo;
    @Autowired
    UserInfoListVO userInfoListVO;
    @Autowired
    UserInfoInformationVO userInfoInformationVO;
    /**
     *
     * 登录
     *
     *
     */
    @RequestMapping(value = "/login.do")
    public ServerResponse login(UserInfo userInfo,HttpServletRequest request)
    {
        if (userInfo.getPassword()!=null&&!userInfo.getPassword().equals(""))
        {
            userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        }


        ServerResponse serverResponse= userService.login(userInfo);
        if (serverResponse.isScuess())
        {
            UserInfo userInfo1=(UserInfo) serverResponse.getData();
            request.getSession().setAttribute(Const.CURRENT_USER,userInfo1);
        }
        return serverResponse;
    }
    /**
     * 注册
     */

    @RequestMapping(value = "register.do")
    public ServerResponse register(UserInfo userInfo)
    {

        return userService.insert(userInfo);
    }
    /**
     * .检查用户名是否有效
     */
    @RequestMapping(value = "check_valid.do")
    public ServerResponse check_valid(String str,String type){
        return userService.check_valid(str,type);
    }
    @RequestMapping(value = "get_user_info.do")
    public ServerResponse get_user_info(HttpServletRequest request)
    {UserInfo userInfo=(UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(1,"用户未登录，无法获取当前用户信息");
        }
        userInfoListVO.assembleUserInfoListVO(userInfo);

        return ServerResponse.createServerResponseBySuccess(null,userInfoListVO);
    }
    @RequestMapping(value = "forget_get_question.do")
    public ServerResponse forget_get_question(String username)
    {
        return userService.forget_get_question(username);

    }
    @RequestMapping(value = "forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,
                                              String question,
                                              String answer,HttpServletRequest request)
    {
        ServerResponse serverResponse= userService.forget_check_answer(username,question,answer);
        return serverResponse;
    }
    @RequestMapping(value = "forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,
                                                String passwordNew,
                                                String forgetToken)
    {
        ServerResponse serverResponse=userService.forget_reset_password(username,passwordNew,forgetToken);
        return serverResponse;
    }
    @RequestMapping(value = "reset_password.do")
    public ServerResponse reset_password(String passwordOld,
                                         String passwordNew,
                                         HttpServletRequest request)
    {
        UserInfo userInfo=(UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(1,"用户未登录，无法获取当前用户信息");
        }
        return userService.reset_password(userInfo.getUsername(),passwordOld,passwordNew);
    }



    @RequestMapping(value = "update_information.do")
    public ServerResponse update_information(String email,
                                             String phone,
                                             String question,
                                             String answer,HttpServletRequest request)
    {
        UserInfo userInfo=(UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(1,"用户未登录");
        }
        UserInfo userInfo1=new UserInfo();
        userInfo1.setId(userInfo.getId());
        userInfo1.setEmail(email);
        userInfo1.setPhone(phone);
        userInfo1.setQuestion(question);
        userInfo1.setAnswer(answer);
        return userService.updateByPrimaryKey(userInfo1);

    }
    @RequestMapping(value = "get_inforamtion.do")
    public ServerResponse get_inforamtion(HttpServletRequest request)
    {UserInfo userInfo=(UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {
            return ServerResponse.createServerResponseByFail(10,"用户未登录，无法获取当前用户信息,status=10强制退出");
        }
        userInfoInformationVO.assembleUserInfoListVO(userInfo);
        return ServerResponse.createServerResponseBySuccess(null,userInfoInformationVO);
    }

    @RequestMapping(value = "logout.do")
    public ServerResponse logout(HttpSession session)
    {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createServerResponseBySuccess("退出成功");
    }




}
