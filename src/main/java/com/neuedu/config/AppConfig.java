package com.neuedu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import sun.awt.SunHints;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@PropertySource("application-dev.yml")
@ConfigurationProperties(prefix = "jdbc")
public class AppConfig {
    @Value("${driver}")
    private String driver;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${call_back}")
    private String call_back;
    @Value("${qr_filepath}")
    private String qr_filepath;

    public String getQr_filepath() {
        return qr_filepath;
    }

    public void setQr_filepath(String qr_filepath) {
        this.qr_filepath = qr_filepath;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public String getCall_back() {
        return call_back;
    }

    public void setCall_back(String call_back) {
        this.call_back = call_back;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
        System.out.println("格式化输出：" + sdf.format(d));
    }

}
