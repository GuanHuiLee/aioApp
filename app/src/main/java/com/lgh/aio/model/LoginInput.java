package com.lgh.aio.model;

/**
 * Created by LiGuanHui on 2019/3/6 11:03
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class LoginInput {
    private String phone;
    private String pwd;

    public LoginInput(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
