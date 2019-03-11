package com.lgh.aio.api;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by niujingtong on 2018/7/7.
 * 模块：
 */
public abstract class MyCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuc(response);
        } else {
            try {
                String error = "";
                if (response.code() == 404) {
                    error = "该请求地址不存在，请检查！";
                } else if (response.code() >= 500) {
                    error = "服务端响应出错，请检查网络或稍后重试！";
                } else {
                    error = response.errorBody().string();
                }
                onFail(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Throwable throwable = t;
        String error = t.getMessage();

        //网络连接错误
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException || throwable instanceof NoRouteToHostException) {
            error = "网络链接失败，请检查网络或稍后重试！";
            throwable.printStackTrace();
        }

        //网络请求超时
        else if (throwable instanceof SocketTimeoutException) {
            error = "网络请求超时，请检查网络或稍后重试！";
            throwable.printStackTrace();
        }

        //其他错误
        else if (throwable instanceof IllegalStateException) {
            error = t.toString();
            throwable.printStackTrace();
        }

        Logger.e(error);
        onFail(error);
    }

    public abstract void onSuc(Response<T> response);

    public abstract void onFail(String message);

}
