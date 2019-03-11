package com.lgh.aio.api;


import com.lgh.aio.App;
import com.zgg.commonlibrary.utils.APPNetWork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LiGuanHui on 2018/12/1 13:18
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class NetWorkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (APPNetWork.isNetworkConnected(App.getApplication())) {
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为1周
            int maxStale = 60 * 60 * 24 * 7;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;

    }
}
