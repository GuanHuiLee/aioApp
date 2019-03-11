package com.lgh.aio.viewmodel;

import android.databinding.adapters.TextViewBindingAdapter;
import android.text.Editable;
import android.view.View;

import com.lgh.aio.Listener.DataListener;
import com.lgh.aio.api.ApiFactory;
import com.lgh.aio.base.BaseObserver;
import com.lgh.aio.base.BaseView;
import com.lgh.aio.base.BaseViewModel;
import com.lgh.aio.model.RegisterInput;
import com.zgg.commonlibrary.utils.NullUtils;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiGuanHui on 2019/3/6 16:58
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class RegisterViewModel extends BaseViewModel {
    private String phone;
    private String pwd;
    private BaseView baseView;
    private DataListener<RegisterInput> dataListener;

    public RegisterViewModel(BaseView baseView, DataListener dataListener) {
        this.baseView = baseView;
        this.dataListener = dataListener;
    }

    public void register(View view) {
        if (NullUtils.isEmptyString(phone)) {
            baseView.showToast("手机号不能为空");
            return;
        }
        if (NullUtils.isEmptyString(pwd)) {
            baseView.showToast("密码不能为空");
            return;
        }

        RegisterInput input = new RegisterInput(phone, pwd);
        ApiFactory.getService().register(input).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        baseView.showToast("注册成功");
                        dataListener.onDataChanged(input);
                    }

                    @Override
                    protected void onStart() {
                        baseView.showProgress("加载中");
                    }

                    @Override
                    protected void onFinish() {
                        baseView.hideProgress();
                    }

                    @Override
                    public void onError(String t) {
                        baseView.showToast(t);
                    }
                });
    }

    public TextViewBindingAdapter.AfterTextChanged phoneChange() {
        return s -> phone = s.toString();
    }

    public TextViewBindingAdapter.AfterTextChanged pwdChange() {
        return s -> pwd = s.toString();
    }
}
