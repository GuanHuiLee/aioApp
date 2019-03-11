package com.lgh.aio.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.adapters.TextViewBindingAdapter;
import android.view.View;

import com.lgh.aio.api.ApiFactory;
import com.lgh.aio.base.BaseObserver;
import com.lgh.aio.base.BaseView;
import com.lgh.aio.model.LoginInput;
import com.lgh.aio.model.LoginResult;
import com.orhanobut.logger.Logger;
import com.zgg.commonlibrary.utils.NullUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiGuanHui on 2019/3/6 10:37
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class LoginViewModel extends BaseObservable {
    private String phone;
    private String pwd;
    private DataListener dataListener;
    private BaseView baseView;

    public LoginViewModel(DataListener dataListener, BaseView baseView) {
        this.dataListener = dataListener;
        this.baseView = baseView;
    }

    public TextViewBindingAdapter.AfterTextChanged phoneChangeListener() {
        return s -> phone = s.toString();
    }


    public TextViewBindingAdapter.AfterTextChanged pwdChangeListener() {
        return s -> pwd = s.toString();
    }


    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(com.lgh.aio.BR.phone);
    }


    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(com.lgh.aio.BR.pwd);
    }

    public void login(View view) {
        if (!NullUtils.isEmptyString(phone) && !NullUtils.isEmptyString(pwd)) {
            ApiFactory.getService().login(new LoginInput(phone, pwd))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new BaseObserver<LoginResult>() {
                        @Override
                        protected void onSuccess(LoginResult loginResult) {
                            Logger.d("onSuccess");
                            dataListener.onDataChanged(loginResult);
                        }

                        @Override
                        protected void onStart() {
                            Logger.d("onStart");
                            baseView.showProgress("加载中");
                        }

                        @Override
                        protected void onFinish() {
                            baseView.hideProgress();
                            Logger.d("onFinish");
                        }

                        @Override
                        public void onError(String t) {
                            Logger.d("onError");
                            baseView.showToast(t);
                        }
                    });

        } else {
            baseView.showToast("用户名或密码不能为空");
        }
    }


    public void register(View view) {
        dataListener.onRegister();
    }

    public interface DataListener {
        void onDataChanged(LoginResult t);

        void onRegister();
    }
}
