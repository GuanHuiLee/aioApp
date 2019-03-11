package com.zgg.commonlibrary.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * ================================================
 * 作    者：lgh
 * 版    本：1.0.0
 * 创建日期：2017/7/11
 * 描    述：应用程序Activity管理类：用于Activity管理和应用程序退出
 * 修订历史：
 * ================================================
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {

        Activity activity = activityStack.lastElement();
        return activity;

    }

    /**
     * Acitivty生命周期结束
     */
    public void destroyActivity(Activity activity) {

        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack == null) return;

        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null) return;

        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) return;

        Activity acti = null;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                acti = activity;
            }
        }
        finishActivity(acti);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) return;

        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束其他activity
     *
     * @param activity
     */
    public void finishOtherActivity(Activity activity) {
        if (activityStack == null) return;

        int i = 0;
        while (i < activityStack.size()) {
            Activity activity2 = (Activity) activityStack.get(i);
            if (activity2 != activity) {
                activity2.finish();
                activityStack.remove(activity2);
                i--;
            }
            i++;
        }
    }
}
