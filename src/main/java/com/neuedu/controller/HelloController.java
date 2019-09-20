package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.config.AppConfig;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HelloController {
    @Autowired
    IUserService userService;
    @RequestMapping("/login")
    public ServerResponse hello(UserInfo userInfo)
    {

//        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
//        UserInfo userInfo1=userService.login(userInfo);
//        return ServerResponse.createServerResponseBySuccess(null,userInfo1);
        return null;
    }
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Autowired
    AppConfig appConfig;
    @RequestMapping("/test")
    public String gerDriver(){

        return appConfig.getDriver()+" "+appConfig.getUsername()+" "+appConfig.getPassword();

    }
    @RequestMapping("/res")
    public ServerResponse res(){

        return ServerResponse.createServerResponseBySuccess(null,"hello");

    }
}
