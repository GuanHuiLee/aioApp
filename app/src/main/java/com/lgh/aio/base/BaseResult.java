package com.lgh.aio.base;


import com.google.gson.annotations.SerializedName;
import com.lgh.aio.App;
import com.lgh.aio.commons.Conts;

/**
 * Created by LiGuanHui on 2018/11/13.
 * 模块：
 */
public class BaseResult<T> {
    private String msg;
    @SerializedName(value = "code", alternate = {"retCode"})
    public int code;
    @SerializedName(value = "data", alternate = {"result"})
    private T data;

    /**
     * 返回结果是否成功
     *
     * @return 返回 true：成功， 否则失败
     * @since 1.0.0
     */
    public boolean isSuccess() {
        //session 失效,退出登录转到登录界面
//        if (code == Conts.NOT_LOGIN) {
//            DataUtil.setSession("");
//            App.getApplication().gotoLogin();
//            return false;
//        }
        return code == Conts.HTTP_OK;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
