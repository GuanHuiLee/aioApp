package com.lgh.aio.model;

/**
 * Created by LiGuanHui on 2019/3/6 11:03
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class LoginResult extends LoginInput {
    private String id;

    public LoginResult(String phone, String pwd) {
        super(phone, pwd);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
