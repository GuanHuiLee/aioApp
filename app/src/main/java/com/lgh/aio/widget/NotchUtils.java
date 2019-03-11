package com.lgh.aio.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by LiGuanHui on 2019/1/24 11:40
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 * 刘海屏适配
 */
public class NotchUtils {

    private static boolean isXiaomi() {
        String manufacturer = Build.MANUFACTURER;
        return "xiaomi".equalsIgnoreCase(manufacturer);
    }

    private static boolean isHuawei() {
        String manufacturer = Build.MANUFACTURER;
        return "huawei".equalsIgnoreCase(manufacturer);
    }

    private static boolean isMeizu() {
        String manufacturer = Build.MANUFACTURER;
        return "meizu".equalsIgnoreCase(manufacturer);
    }

    private static boolean isOppo() {
        String manufacturer = Build.MANUFACTURER;
        return "oppo".equalsIgnoreCase(manufacturer);
    }

    private static boolean isVivo() {
        String manufacturer = Build.MANUFACTURER;
        return "vivo".equalsIgnoreCase(manufacturer);
    }

    public static int getNotchHeight(Context context) {
        if (isXiaomi()) {
            return getNotchSizeAtXiaomi(context);
        } else if (isHuawei()) {
            return getNotchSizeAtHuawei(context);
        } else if (isOppo())
            return getNotchSizeAtOppo(context);
        return 80;
    }

    /**
     * 判断是否是刘海屏
     *
     * @return
     */
    public static boolean hasNotchScreen(Activity activity) {
        if (getInt("ro.miui.notch", activity) == 1 || hasNotchAtHuawei(activity) || hasNotchAtOPPO(activity)
                || hasNotchAtVivo(activity) || isAndroidP(activity) != null) { //TODO 各种品牌
            return true;
        }

        return false;
    }

    /**
     * Android P 刘海屏判断
     *
     * @param activity
     * @return
     */
    private static DisplayCutout isAndroidP(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null)
                return windowInsets.getDisplayCutout();
        }
        return null;
    }

    /**
     * 小米刘海屏判断.
     *
     * @return 0 if it is not notch ; return 1 means notch
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    private static int getInt(String key, Activity activity) {
        int result = 0;
        if (isXiaomi()) {
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = new String(key);
                params[1] = new Integer(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 华为刘海屏判断
     *
     * @return
     */
    private static boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Logger.d("hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Logger.d("hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Logger.d("hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    private static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    private static final int VIVO_FILLET = 0x00000008;//是否有圆角

    /**
     * VIVO刘海屏判断
     *
     * @return
     */
    private static boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            Logger.d("hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Logger.d("hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Logger.d("hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    /**
     * OPPO刘海屏判断
     *
     * @return
     */
    private static boolean hasNotchAtOPPO(Activity context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");

    }

    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    private static int getNotchSizeAtHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Logger.d("Notch", "getNotchSizeAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Logger.d("Notch", "getNotchSizeAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Logger.d("Notch", "getNotchSizeAtHuawei Exception");
        } finally {
            return ret[1];
        }
    }

    private static int getNotchSizeAtOppo(Context context) {

        int statusBarHeight = 0;

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {

            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

        }

        return statusBarHeight;

    }

    private static int getNotchSizeAtXiaomi(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        Logger.d("getStatusBarHeight: " + statusBarHeight1);
        return statusBarHeight1;

    }

}
