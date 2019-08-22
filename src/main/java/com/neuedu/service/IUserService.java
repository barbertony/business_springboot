package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserService {
    public ServerResponse login(UserInfo userInfo)throws MyException;
    List<UserInfo>findAll();
    ServerResponse updateByPrimaryKey(UserInfo record);
    int deleteByPrimaryKey(Integer id);
    ServerResponse insert(UserInfo record);
    ServerResponse check_valid(String str, String type);
    ServerResponse forget_get_question(String username);
    ServerResponse forget_check_answer(String username, String question, String answer);
    ServerResponse reset_password(String username,
                                  String passwordOld,
                                  String passwordNew);
    ServerResponse forget_reset_password(String username,
                                         String passwordNew,
                                         String forgetToken);
    ServerResponse list(String pageSize,String pageNum);


}
