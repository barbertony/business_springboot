package com.neuedu.vo;

import com.neuedu.pojo.UserInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Scope("prototype")
public class UserInfoListVO {
    private int id;
    private String username;
    private  String email;
    private String phone;
    private Integer role;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public void assembleUserInfoListVO(UserInfo userInfo)
    {
        this.id=userInfo.getId();
        this.username=userInfo.getUsername();
        this.email=userInfo.getEmail();
        this.phone=userInfo.getPhone();
        this.role=userInfo.getRole();
        this.createTime=userInfo.getCreateTime();
        this.updateTime=userInfo.getUpdateTime();
    }
}
