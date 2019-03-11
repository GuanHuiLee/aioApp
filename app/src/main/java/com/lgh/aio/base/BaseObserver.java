package com.lgh.aio.base;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Observer;

/**
 * Created by LiGuanHui on 2019/3/6 14:58
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public abstract class BaseObserver<T> implements Observer<BaseResult<T>> {
    protected BaseObserver() {
        onStart();
    }

    @Override
    public void onCompleted() {
        onFinish();
    }

    @Override
    public void onError(Throwable throwable) {
        String error = throwable.getMessage();

        //网络连接错误
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException || throwable instanceof NoRouteToHostException) {
            error = "链接失败，请检查网络或稍后重试";
            throwable.printStackTrace();
        }

        //网络请求超时
        else if (throwable instanceof SocketTimeoutException) {
            error = "请求超时，请检查网络或稍后重试";
            throwable.printStackTrace();
        }

        //其他错误
        else if (throwable instanceof IllegalStateException) {
            error = throwable.toString();
            throwable.printStackTrace();
        }
        onError(error);
    }

    @Override
    public void onNext(BaseResult<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onSuccess(t);
        } else {
            String error = "";
            if (value.code == 404) {
                error = "请求无效，请检查";
            } else if (value.code >= 500) {
                error = "链接失败，请检查网络或稍后重试";
            } else {
                error = value.getMsg();
            }
            onError(error);
        }
    }

    protected abstract void onSuccess(T t);

    protected abstract void onStart();

    protected abstract void onFinish();

    public abstract void onError(String t);
}
