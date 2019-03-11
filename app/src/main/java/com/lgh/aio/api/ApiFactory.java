package com.lgh.aio.api;


import com.lgh.aio.App;
import com.lgh.aio.commons.Conts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LiGuanHui on 2018/7/6.
 */
public class ApiFactory {
    private static ApiService apiService;

    private static final OkHttpClient mClient;

    private static final Retrofit mRetrofit;

    static {
        File httpCacheDirectory = new File(App.getApplication().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        mClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .addNetworkInterceptor(new NetWorkInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Conts.BASE_IP)
                .client(mClient)//设置读写连接超时
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static ApiService getService() {
        if (apiService == null)
            apiService = mRetrofit.create(ApiService.class);
        return apiService;
    }
}