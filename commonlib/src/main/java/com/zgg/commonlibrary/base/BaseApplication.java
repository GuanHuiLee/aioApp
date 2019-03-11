package com.zgg.commonlibrary.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zgg.commonlibrary.BuildConfig;


/**
 * ================================================
 * 作    者：loujingying@aliyun.com
 * 版    本：1.0.0
 * 创建日期：2017/7/11
 * 描    述：Application基类，用于继承
 * 修订历史：
 * ================================================
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication mApp = null;
    private static Handler mMainHandler = null;


    /**
     * App Activity 自定义栈管理
     */
    public AppManager appManager;

    public BaseApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mMainHandler = new Handler();

        //初始化日志工具类
        initLogger("Aio");


        // 初始化应用
        initApplication();
    }

    public static BaseApplication getApp() {
        return mApp;
    }

    private void initLogger(String tag) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag) // 全局tag
                .showThreadInfo(false)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 初始化应用
     */
    protected void initApplication() {
    }

    /**
     * 退出登录转到登录界面
     */
    public void loginOut() {

    }


    /**
     * 转到登录界面
     */
    public void gotoLogin() {

    }


    public static final BaseApplication getApplication() {
        return mApp;
    }

    public static final Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    public static final Handler getMainHandler() {
        return mMainHandler;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
