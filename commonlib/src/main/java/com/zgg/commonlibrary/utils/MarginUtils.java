package com.zgg.commonlibrary.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiGuanHui on 2019/2/25 09:57
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class MarginUtils {

    public static void setMargin(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
