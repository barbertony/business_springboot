package com.neuedu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCacheUtils;
import com.neuedu.vo.UserInfoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service

public class UserServiceImpl implements IUserService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public ServerResponse login(UserInfo userInfo) throws MyException {
        if (userInfo==null||userInfo.getUsername()==null||userInfo.getUsername().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        System.out.println(userInfo.getPassword());
        if (userInfo.getPassword()==null||userInfo.getPassword().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户密码不能为空");
        }
        int result=userInfoMapper.exsitsUsername(userInfo.getUsername());
        if (result<1)
        {
            return  ServerResponse.createServerResponseByFail(101,"用户名不存在");
        }

        UserInfo userInfo1=userInfoMapper.findByUsernameAndPassword(userInfo);
        if(userInfo1==null)
        {
            return  ServerResponse.createServerResponseByFail(1,"密码错误");
        }

        return  ServerResponse.createServerResponseBySuccess(null,userInfo1);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectAll();
    }

    @Override
    public ServerResponse updateByPrimaryKey(UserInfo record) {
        int result= userInfoMapper.updateByPrimaryKey(record);
        if (result>0)
        {
            return ServerResponse.createServerResponseBySuccess("更新个人信息成功");
        }
        return ServerResponse.createServerResponseByFail(1,"更新用户信息失败");
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ServerResponse insert(UserInfo record) {
        if(record.getUsername()==null||record.getUsername().equals("") ){//用户名为空

            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");

        }
        if(record.getPassword()==null||record.getPassword().equals("") ){//密码为空

            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");

        }
        record.setPassword(MD5Utils.getMD5Code(record.getPassword()));
        if (record.getEmail()==null||record.getEmail().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");
        }
        if (record.getAnswer()==null||record.getAnswer().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");
        }
        if (record.getQuestion()==null||record.getQuestion().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");
        }
        if (record.getPhone()==null||record.getPhone().equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"注册信息不能为空");
        }
        if (record.getRole()==null)
        {
            record.setRole(1);
        }
        int result=userInfoMapper.exsitsUsername(record.getUsername());
        if (result>0)
        {
            return  ServerResponse.createServerResponseByFail(1,"用户已存在");
        }
        result=userInfoMapper.exsitsEmail(record.getEmail());
        if (result>0)
        {
            return  ServerResponse.createServerResponseByFail(1,"邮箱已注册");
        }
        result=userInfoMapper.insert(record);
        return ServerResponse.createServerResponseBySuccess("用户注册成功");
    }

    @Override
    public ServerResponse check_valid(String str, String type) {
        if (type.equals("email"))
        {
            int result=userInfoMapper.exsitsEmail(str);
            if (result>0)
            {
                return ServerResponse.createServerResponseByFail(1,"邮箱已注册");
            }
            return ServerResponse.createServerResponseBySuccess("校验成功");
        }
        int result=userInfoMapper.exsitsUsername(str);
        if (result>0)
        {
            return ServerResponse.createServerResponseByFail(1,"用户名已存在");
        }
        return ServerResponse.createServerResponseBySuccess("校验成功");
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        if (username==null||username.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        int result=userInfoMapper.exsitsUsername(username);
        if (result==0)
        {
            return  ServerResponse.createServerResponseByFail(101,"用户名不存在");
        }
        String question=userInfoMapper.findQuestionByUsername(username);
        if (question==null||question.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(1,"该用户未设置找回密码问题");
        }
        return ServerResponse.createServerResponseBySuccess(null,question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        if (username==null||username.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        if (question==null||question.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"问题不能为空");
        }
        if (answer==null||answer.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"答案不能为空");
        }

        int result=userInfoMapper.findByUsernameAndQuestionAndAnswer(username,question,answer);
        if (result==1)
        {String forgetToken= UUID.randomUUID().toString();
            TokenCacheUtils.setKey(username,forgetToken);
            return ServerResponse.createServerResponseBySuccess(null,forgetToken);
        }
        return ServerResponse.createServerResponseByFail(1,"答案错误");
    }

    @Override
    public ServerResponse reset_password(String username, String passwordOld, String passwordNew) {
        if (username==null||username.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        if (passwordOld==null||passwordOld.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"密码不能为空");
        }
        if (passwordNew==null||passwordNew.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"密码不能为空");
        }
        int result=userInfoMapper.selectByUsernameAndPassword(username,MD5Utils.getMD5Code(passwordOld));
        if (result!=1)
        {
            return  ServerResponse.createServerResponseByFail(1,"旧密码输入错误");
        }
        userInfoMapper.resetPasswordByUsername(username,MD5Utils.getMD5Code(passwordNew));

        return ServerResponse.createServerResponseBySuccess(null,"修改密码成功");
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        if (username==null||username.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        if (passwordNew==null||passwordNew.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"密码不能为空");
        }
        if (forgetToken==null||forgetToken.equals(""))
        {
            return  ServerResponse.createServerResponseByFail(100,"token不能为空");
        }

        String token=TokenCacheUtils.getKey(username);
        if (token==null||token.equals(""))
        {
            return ServerResponse.createServerResponseByFail(103,"token已经失效");
        }
        if (!token.equals(forgetToken))
        {
            return ServerResponse.createServerResponseByFail(104,"非法的token");
        }
        passwordNew=MD5Utils.getMD5Code(passwordNew);
        int result=userInfoMapper.resetPasswordByUsername(username,passwordNew);
        if (result>0)
        {
            return ServerResponse.createServerResponseBySuccess("修改密码成功");
        }
        return ServerResponse.createServerResponseByFail(1,"修改密码操作失败");
    }

    @Override
    public ServerResponse list(String pageSize, String pageNum) {
        int IpageSize=Integer.parseInt(pageSize);
        int IpageNum=Integer.parseInt(pageNum);
        Page page=PageHelper.startPage(IpageNum,IpageSize);

        List<UserInfo> userInfoList=userInfoMapper.selectAll();

        List<UserInfoListVO> userInfoListVOList= Lists.newArrayList();
        if (userInfoList!=null&&userInfoList.size()>0)
        {
            for (UserInfo userInfo:userInfoList)
            {
                UserInfoListVO userInfoListVO=assembleUserInfoListVO(userInfo);
                userInfoListVOList.add(userInfoListVO);
            }
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(userInfoListVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);

    }
    private UserInfoListVO assembleUserInfoListVO(UserInfo userInfo)
    {
        UserInfoListVO userInfoListVO=new UserInfoListVO();
        userInfoListVO.setId(userInfo.getId());
        userInfoListVO.setUsername(userInfo.getUsername());
        userInfoListVO.setEmail(userInfo.getEmail());
        userInfoListVO.setPhone(userInfo.getPhone());
        userInfoListVO.setRole(userInfo.getRole());
        userInfoListVO.setCreateTime(userInfo.getCreateTime());
        userInfoListVO.setUpdateTime(userInfo.getUpdateTime());
        return userInfoListVO;


    }


}
