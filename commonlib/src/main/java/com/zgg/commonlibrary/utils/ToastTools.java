package com.zgg.commonlibrary.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zgg.commonlibrary.base.AppManager;
import com.zgg.commonlibrary.base.BaseApplication;

import java.lang.ref.WeakReference;

/**
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : 吐司相关工具类
 * 为了解决小米手上弹的Toast会默认加上应用名的问题
 * 此代码是从author处copy过来的
 */
public class ToastTools {
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId = -1;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int yOffset = (int) (64 * BaseApplication.getApp().getResources().getDisplayMetrics().density + 0.5);
    private static int bgColor = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor = COLOR_DEFAULT;

    private ToastTools() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastTools.gravity = gravity;
        ToastTools.xOffset = xOffset;
        ToastTools.yOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(final int backgroundColor) {
        ToastTools.bgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(final int bgResource) {
        ToastTools.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(final int msgColor) {
        ToastTools.msgColor = msgColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShort(final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLong(final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    private static void show(final int resId, final int duration) {
        show(BaseApplication.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(final int resId, final int duration, final Object... args) {
        show(String.format(BaseApplication.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {
                    cancel();
                    sToast = Toast.makeText(BaseApplication.getApp(), null, duration);
                    sToast.setText(text);
                    TextView tvMessage = sToast.getView().findViewById(android.R.id.message);
                    TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                    tvMessage.setTextColor(msgColor);
                    setBgAndGravity();
                    sToast.show();
                } catch (Exception e) {
                }
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(BaseApplication.getApp());
                sToast.setView(view);
                sToast.setDuration(duration);
                setBgAndGravity();
                sToast.show();
            }
        });
    }

    private static void setBgAndGravity() {
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
        }
        sToast.setGravity(gravity, xOffset, yOffset);
    }

    private static View getView(final int layoutId) {
        if (sLayoutId == layoutId) {
            if (sViewWeakReference != null) {
                final View toastView = sViewWeakReference.get();
                if (toastView != null) {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) BaseApplication.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        sLayoutId = layoutId;
        return toastView;
    }
}
