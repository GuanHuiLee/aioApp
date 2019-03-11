package com.lgh.aio.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lgh.aio.BR;

/**
 * Created by LiGuanHui on 2019/3/7 11:01
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class User extends BaseObservable {
    private String name;
    private String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }


}
