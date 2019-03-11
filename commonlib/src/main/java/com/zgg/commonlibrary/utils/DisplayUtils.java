package com.zgg.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zgg.commonlibrary.base.BaseApplication;


/**
 * ================================================
 * 作    者：loujingying@aliyun.com
 * 版    本：1.0.0
 * 创建日期：2017/8/4
 * 描    述：屏幕工具类
 * 修订历史：
 * ================================================
 */

public abstract class DisplayUtils {

    /**
     * 是否横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 是否竖屏
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     * @deprecated 使用 {@link #getScreenWidth()} 代替
     */
    @Deprecated
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = BaseApplication.getApplication().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return
     * @deprecated 使用 {@link #getScreenHeight()} 代替
     */
    @Deprecated
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = BaseApplication.getApplication().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    /*public static int getScreenWidthPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }*/

    /**
     * 获取屏幕高度
     */
    /*public static int getScreenHeightPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }*/

    /**
     * 返回手机屏幕的宽度
     *
     * @return
     */
    public static int getWindowWidth() {
        WindowManager windowManager = getWindowManager();
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 返回手机屏幕的高度
     *
     * @return
     */
    public static int getWindowHeight() {
        WindowManager windowManager = getWindowManager();
        return windowManager.getDefaultDisplay().getHeight();
    }

    private static WindowManager getWindowManager() {
        return (WindowManager) BaseApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取屏幕像素密度
     *
     * @param context
     * @return
     */
    public static int getScreenDensityDPI(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取TitleBar的高度, 这个方法不能在 onCreate(),onStart(),onResume()方法中使用
     *
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        int statusBarHeight = getStatusBarHeight(activity);
        int contentViewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;
        return titleBarHeight < 0 ? 0 : titleBarHeight;
    }

    /**
     * 获取statusbar的高度, 这个方法不能在 onCreate(),onStart(),onResume()方法中使用
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 获取statusbar的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight2(Activity activity) {
        int statusBarHeight = getStatusBarHeight(activity);
        if (0 == statusBarHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int id = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusBarHeight = activity.getResources().getDimensionPixelSize(id);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * dip 转 px ，通过手机密度
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dip2px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return (int) (dipToPx(context, dp) + 0.5f);
    }

    /**
     * dip 转 px
     *
     * @param context
     * @param dp
     * @return
     */
    private static float dipToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }

    /**
     * px 转 dip，通过手机密度
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return (int) (pxToDip(context, px) + 0.5f);
    }

    /**
     * px 转 dip
     *
     * @param context
     * @param px
     * @return
     */
    private static float pxToDip(Context context, float px) {
        if (context == null) {
            return -1;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return px / scale;
    }

    /**
     * px 转 sp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2sp(Context context, float px) {
        return (int) (pxToSp(context, px) + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context
     * @param px
     * @return
     */
    private static float pxToSp(Context context, float px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return px / fontScale;
    }

    /**
     * sp 转 px
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        return (int) (spToPx(context, sp) + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context
     * @param sp
     * @return
     */
    private static float spToPx(Context context, float sp) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale;
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showSoftInputFromWindow(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) view
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动光标到输入框末尾
     *
     * @param et
     */
    public static void cursorToEnd(EditText et) {
        Editable text = et.getText();
        Spannable spanText = text;
        Selection.setSelection(spanText, text.length());
    }
}
